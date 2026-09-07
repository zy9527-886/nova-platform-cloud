# System Management CRUD and Binding Design

## Goal

Replace the mock/static implementations of user, role, and menu management in `nova-platform-web` with the real `nova-platform-cloud/nova-system` APIs. Complete CRUD, user-role binding, and role-menu binding while treating backend field names and value semantics as the public contract.

## Scope

The change covers these three management pages:

- User management: filtered pagination, create, detail/edit, single and batch delete, and role assignment.
- Role management: filtered pagination, create, detail/edit, single and batch delete, and menu assignment.
- Menu management: ordered tree query, create root/child menu, detail/edit, and delete.

Authentication, organization management, password reset, route generation, and redesign of the global authorization model are outside this change.

## API Contract

All browser requests continue to use the existing `/api` proxy. System-service routes use the gateway prefix `/system`, followed by the current controller path.

### Users

- `POST /system/sysUser/page`: accepts `PageQuery<SysUserVo, SysUserDto>` and returns a MyBatis-Flex page. Each record includes its assigned `roles`.
- `GET /system/sysUser/getById/{userId}`: returns `SysUserVo`, including assigned `roles`.
- `POST /system/sysUser/saveOrUpdate`: accepts `SysUserDto`; `userRolList` is replaced atomically when editing and inserted atomically when creating.
- `DELETE /system/sysUser/removeById/{userId}` and `DELETE /system/sysUser/removeByIds`: delete user-role rows and users in one transaction.

The user fields consumed by the web application are `userId`, `userNm`, `rmk`, `idTyp`, `idNo`, `realNm`, `tel`, `orgCd`, `stus`, `icon`, `creTm`, and `roles`. Password fields and login/lock counters remain backend-managed. New users receive the configured initial password.

### Roles

- Existing generic routes remain: `POST /system/sysRol/page`, `GET /system/sysRol/getById/{rolId}`, `POST /system/sysRol/saveOrUpdate`, `DELETE /system/sysRol/removeById/{rolId}`, and `DELETE /system/sysRol/removeByIds`.
- `GET /system/sysRol/menuIds/{rolId}` returns the menu IDs assigned to the role.
- `POST /system/sysRol/bindMenus` accepts `{ "rolId": "...", "menuIds": ["..."] }` and atomically replaces the role-menu rows.
- Role deletion removes user-role and role-menu rows in the same transaction before deleting the role.

The role fields are `rolId`, `rolNm`, `rolCd`, `rolLv`, `rolDesc`, `orgId`, `isPub`, and `creTm`.

### Menus

- `GET /system/sysMenu/list` returns every menu ordered by `sort` and `menuId`; the web application builds the tree using `prentId`.
- Existing generic detail and save routes remain: `GET /system/sysMenu/getById/{menuId}` and `POST /system/sysMenu/saveOrUpdate`.
- `DELETE /system/sysMenu/removeById/{menuId}` deletes a leaf menu after removing role-menu rows. A menu with children is rejected with a business error.

The menu fields are `menuId`, `menuNm`, `permCd`, `path`, `prentId`, `sort`, `icon`, `typ`, `isDsp`, `menuSource`, and `creTm`. `typ` follows the backend definition (`0` button, `1` menu). A navigational directory is represented by a menu with children, not a third type. Mock-only fields such as `redirect`, `componentName`, `isFrame`, `keepAlive`, and `status` are removed from the management UI.

## Backend Design

`SysUserService` becomes the transaction boundary for user persistence and user-role replacement. It enriches page/detail results by loading role rows in batches, avoiding one query per user. The update path uses the submitted `userId` for replacement rows; it must never generate or write a new ID while editing.

`SysRolService` owns role-menu replacement and association cleanup on deletion. A small request DTO expresses the `rolId/menuIds` contract. `SysMenuService` owns ordered listing, child detection, role-menu cleanup, and leaf deletion. Controllers expose these business operations while retaining the existing route naming style.

Association inputs are normalized by removing null, blank, and duplicate IDs. Empty lists mean “clear all assignments.” Transaction rollback prevents a parent row from being saved or deleted while its association update only partially succeeds.

## Frontend Design

Create focused API modules for users, roles, and menus plus shared response/page types. Snowflake IDs remain strings everywhere. Components consume the backend response envelope (`code`, `msg`, `data`) and MyBatis-Flex pagination fields (`pageNumber`, `pageSize`, `totalRow`, `records`).

The user page uses backend query fields and opens a user modal that loads all roles for a multi-select. The modal sends role selections as `userRolList: [{ rolId }]`. Editing loads fresh detail instead of relying only on the table row.

The role page provides searchable pagination and a create/edit modal for backend fields. Its permission action loads the complete menu list and selected menu IDs, displays a checkable tree, and submits `bindMenus`.

The menu page converts the flat ordered response to an Ant Design tree table. Its modal edits only backend-supported fields and supports choosing a parent. Root uses `prentId: "0"`. Parent options exclude the edited node and all its descendants to prevent cycles in the UI.

Single and batch deletes require confirmation and refresh the current page. When the last record on a non-first page is removed, the page number moves back one page before refresh.

## Error Handling

The Axios response interceptor recognizes backend `msg` as well as `message`, rejects unsuccessful envelopes, and preserves the backend message for the page-level handler. Forms remain open on request failure. Loading flags are reset in `finally` blocks, and validation failures do not emit network-error messages.

Backend services reject invalid role/menu references, missing parent objects, menu cycles, and deletion of menus with children. Duplicate association values are normalized before insert so database unique constraints do not become user-visible errors.

## Testing and Verification

Backend tests cover:

- user create/update role replacement, including the update-ID regression;
- page/detail role enrichment;
- cascading association cleanup on user and role deletion;
- role-menu replacement, including empty-list clearing and duplicate normalization;
- ordered menu listing and rejection of deleting a parent menu.

Frontend tests cover flat-menu tree construction, descendant exclusion, request payload mapping, and pagination fallback after deletion. Components are also checked by TypeScript/Vite build.

Integration verification uses the running gateway at `http://127.0.0.1:8866/system`. It creates uniquely named temporary user, role, and menu records, verifies read/update and both bindings, then deletes those records and confirms cleanup. `docs/test/system.http` is expanded with reproducible requests for all three modules and both binding flows.

## Existing Worktree Changes

Existing unrelated changes in both repositories are preserved. The untracked `SysUserRoleVo.java` and `SysUserServiceImplTest.java` are treated as in-progress user work: their intent is incorporated where compatible, and they are not overwritten or discarded without review.
