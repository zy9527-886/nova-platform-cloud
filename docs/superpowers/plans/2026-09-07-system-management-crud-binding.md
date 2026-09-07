# System Management CRUD and Binding Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Connect the web user, role, and menu modules to the real system service and complete CRUD plus user-role and role-menu binding.

**Architecture:** Backend service methods own transactional aggregate changes and association cleanup; controllers expose focused routes alongside the existing CRUD convention. The Vue application uses backend-native field names and small pure helpers for tree/pagination behavior, with typed API modules as the only HTTP boundary.

**Tech Stack:** Java 21, Spring Boot 3, MyBatis-Flex, JUnit 5/Mockito, Vue 3, TypeScript, Ant Design Vue, Axios, Vite, Vitest.

**Spec:** `docs/superpowers/specs/2026-09-07-system-management-crud-binding-design.md`

## Global Constraints

- Preserve unrelated existing worktree changes in both repositories.
- Keep every database ID as a string in browser code.
- Backend field names and values are the public contract; do not retain mock-only aliases.
- Association replacement and cascading cleanup must be transactional.
- Integration fixtures must use unique names and be deleted after verification.

---

### Task 1: User Aggregate CRUD and Role Enrichment

**Files:**
- Modify: `nova-common/nova-common-database/src/main/java/org/nova/platform/system/entity/vo/SysUserVo.java`
- Modify: `nova-common/nova-common-database/src/main/java/org/nova/platform/system/dao/SysUserDao.java`
- Modify: `nova-system/src/main/java/org/nova/platform/system/service/SysUserService.java`
- Modify: `nova-system/src/main/java/org/nova/platform/system/service/impl/SysUserServiceImpl.java`
- Modify: `nova-system/src/main/java/org/nova/platform/system/controller/SysUserController.java`
- Modify: `nova-system/src/test/java/org/nova/platform/system/service/impl/SysUserServiceImplTest.java`

**Interfaces:**
- Consumes: `SysUserDto.userRolList`, `SysUserDao`, `SysUserRolDao`, and `SysRolDao`.
- Produces: `Page<SysUserVo> selectPage(...)`, `SysUserVo getDetail(Serializable)`, transactional `saveOrUpdate`, `removeByIdWithRoles`, and `removeByIdsWithRoles`.

- [ ] **Step 1: Write failing service tests**

Add tests that assert an update writes every `SysUserRol.userId` as the submitted ID, page/detail results expose real role rows, and delete clears association rows before user rows. The regression assertion is:

```java
assertTrue(user.getUserRolList().stream()
        .allMatch(item -> "existing-user".equals(item.getUserId())));
```

- [ ] **Step 2: Run tests and verify RED**

Run: `mvn -pl nova-system -am -DskipTests=false -Dtest=SysUserServiceImplTest -Dsurefire.failIfNoSpecifiedTests=false test`

Expected: failures because update uses a generated ID and page/detail/delete enrichment methods are absent.

- [ ] **Step 3: Implement the aggregate methods**

Add Lombok accessors to `SysUserVo.roles`, batch-query role rows by user IDs, group them, and attach them to records. Normalize role IDs before inserts. On update, use `user.getUserId()`; on create, use the generated ID. Add transactional association cleanup methods and route controller delete/detail calls through them.

- [ ] **Step 4: Run focused tests and verify GREEN**

Run the same Maven command and expect all `SysUserServiceImplTest` tests to pass.

- [ ] **Step 5: Commit the backend user aggregate**

```bash
git add nova-common/nova-common-database/src/main/java/org/nova/platform/system/entity/vo/SysUserVo.java nova-common/nova-common-database/src/main/java/org/nova/platform/system/dao/SysUserDao.java nova-system/src/main/java/org/nova/platform/system/service/SysUserService.java nova-system/src/main/java/org/nova/platform/system/service/impl/SysUserServiceImpl.java nova-system/src/main/java/org/nova/platform/system/controller/SysUserController.java nova-system/src/test/java/org/nova/platform/system/service/impl/SysUserServiceImplTest.java
git commit -m "feat: complete user role aggregate CRUD"
```

### Task 2: Role-Menu Binding and Safe Role Deletion

**Files:**
- Create: `nova-common/nova-common-database/src/main/java/org/nova/platform/system/entity/dto/SysRolMenuBindDto.java`
- Modify: `nova-system/src/main/java/org/nova/platform/system/service/SysRolService.java`
- Modify: `nova-system/src/main/java/org/nova/platform/system/service/impl/SysRolServiceImpl.java`
- Modify: `nova-system/src/main/java/org/nova/platform/system/controller/SysRolController.java`
- Create: `nova-system/src/test/java/org/nova/platform/system/service/impl/SysRolServiceImplTest.java`

**Interfaces:**
- Consumes: `SysRolMenuDao`, `SysUserRolDao`, and role/menu IDs.
- Produces: `List<String> getMenuIds(String rolId)`, `boolean bindMenus(SysRolMenuBindDto)`, and transactional association-aware delete methods.

- [ ] **Step 1: Write failing binding tests**

Cover duplicate/null/blank menu ID normalization, empty-list clearing, missing role rejection, and removal of both association kinds before deleting roles.

```java
dto.setRolId("role-1");
dto.setMenuIds(List.of("menu-1", "menu-1", "menu-2"));
assertTrue(service.bindMenus(dto));
```

- [ ] **Step 2: Run tests and verify RED**

Run: `mvn -pl nova-system -am -DskipTests=false -Dtest=SysRolServiceImplTest -Dsurefire.failIfNoSpecifiedTests=false test`

Expected: compilation/test failure because the DTO and service methods do not exist.

- [ ] **Step 3: Implement role binding and deletion**

Create the DTO with `@NotBlank rolId` and `List<String> menuIds`. Replace role-menu rows in one transaction, validate referenced IDs, expose `GET /menuIds/{rolId}` and `POST /bindMenus`, and override controller delete routes to use cleanup methods.

- [ ] **Step 4: Run focused tests and verify GREEN**

Run the Task 2 Maven command and expect all tests to pass.

- [ ] **Step 5: Commit role binding**

```bash
git add nova-common/nova-common-database/src/main/java/org/nova/platform/system/entity/dto/SysRolMenuBindDto.java nova-system/src/main/java/org/nova/platform/system/service/SysRolService.java nova-system/src/main/java/org/nova/platform/system/service/impl/SysRolServiceImpl.java nova-system/src/main/java/org/nova/platform/system/controller/SysRolController.java nova-system/src/test/java/org/nova/platform/system/service/impl/SysRolServiceImplTest.java
git commit -m "feat: add role menu binding APIs"
```

### Task 3: Ordered Menu Listing and Safe Menu CRUD

**Files:**
- Modify: `nova-system/src/main/java/org/nova/platform/system/service/SysMenuService.java`
- Modify: `nova-system/src/main/java/org/nova/platform/system/service/impl/SysMenuServiceImpl.java`
- Modify: `nova-system/src/main/java/org/nova/platform/system/controller/SysMenuController.java`
- Create: `nova-system/src/test/java/org/nova/platform/system/service/impl/SysMenuServiceImplTest.java`

**Interfaces:**
- Consumes: `SysMenuDao` and `SysRolMenuDao`.
- Produces: `List<SysMenu> listOrdered()` and `boolean removeLeafById(Serializable menuId)`.

- [ ] **Step 1: Write failing menu behavior tests**

Assert ordering requests `sort` then `menuId`, deleting a parent is rejected, and leaf deletion clears role-menu rows before the menu.

- [ ] **Step 2: Run tests and verify RED**

Run: `mvn -pl nova-system -am -DskipTests=false -Dtest=SysMenuServiceImplTest -Dsurefire.failIfNoSpecifiedTests=false test`

Expected: failure because the behavior methods do not exist.

- [ ] **Step 3: Implement list and guarded deletion**

Expose `GET /sysMenu/list`, override the single-delete controller route, test child existence with `prentId`, reject parent deletion with a business exception, then remove role-menu rows and the leaf in one transaction.

- [ ] **Step 4: Run all backend tests and verify GREEN**

Run: `mvn -pl nova-system -am -DskipTests=false -Dsurefire.failIfNoSpecifiedTests=false test`

Expected: all system tests pass.

- [ ] **Step 5: Commit menu behavior**

```bash
git add nova-system/src/main/java/org/nova/platform/system/service/SysMenuService.java nova-system/src/main/java/org/nova/platform/system/service/impl/SysMenuServiceImpl.java nova-system/src/main/java/org/nova/platform/system/controller/SysMenuController.java nova-system/src/test/java/org/nova/platform/system/service/impl/SysMenuServiceImplTest.java
git commit -m "feat: add safe menu management APIs"
```

### Task 4: Typed Frontend API and Pure Data Helpers

**Files:**
- Create: `src/api/system/types.ts`
- Create: `src/api/system/user.ts`
- Create: `src/api/system/role.ts`
- Replace: `src/api/menu.ts`
- Create: `src/views/system/shared/data.ts`
- Create: `src/views/system/shared/data.test.ts`
- Modify: `src/utils/request.ts`
- Modify: `package.json`
- Modify: `pnpm-lock.yaml`

**Interfaces:**
- Consumes: backend envelope and MyBatis-Flex page shapes.
- Produces: typed CRUD/binding functions, `buildMenuTree`, `excludedMenuIds`, and `pageAfterDelete`.

- [ ] **Step 1: Add Vitest and write failing helper tests**

Tests use literal fixtures with string IDs and assert orphan-safe tree construction, descendant exclusion, and page fallback behavior.

```ts
expect(buildMenuTree([
  { menuId: '1', menuNm: 'root', prentId: '0', sort: 1, typ: '1', isDsp: 1 },
  { menuId: '2', menuNm: 'child', prentId: '1', sort: 2, typ: '1', isDsp: 1 },
])[0].children?.[0].menuId).toBe('2')
```

- [ ] **Step 2: Run tests and verify RED**

Run: `pnpm vitest run src/views/system/shared/data.test.ts`

Expected: failure because helper functions are missing.

- [ ] **Step 3: Implement typed API modules and helpers**

Use `/system/sysUser`, `/system/sysRol`, and `/system/sysMenu` paths. Send user roles as `{ userId?, rolId }[]`, use `current/size/query` for user pagination and `pageNumber/pageSize` for response data, and preserve all IDs as strings. Update the interceptor to use `res.msg || res.message`.

- [ ] **Step 4: Run helper tests and type check**

Run: `pnpm vitest run src/views/system/shared/data.test.ts`

Run: `pnpm exec vue-tsc --noEmit`

Expected: both commands pass.

- [ ] **Step 5: Commit frontend API foundation**

```bash
git add src/api/system src/api/menu.ts src/views/system/shared src/utils/request.ts package.json pnpm-lock.yaml
git commit -m "feat: add typed system management APIs"
```

### Task 5: User, Role, and Menu Pages

**Files:**
- Modify: `src/views/system/user/index.vue`
- Modify: `src/views/system/user/components/UserModal.vue`
- Create: `src/views/system/role/components/RoleModal.vue`
- Create: `src/views/system/role/components/RolePermissionModal.vue`
- Modify: `src/views/system/role/index.vue`
- Modify: `src/views/system/menu/index.vue`

**Interfaces:**
- Consumes: Task 4 API functions/types/helpers.
- Produces: complete CRUD and binding interactions for all three management routes.

- [ ] **Step 1: Replace user static data and modal simulation**

Use backend filters (`userNm`, `realNm`, `stus`), fetch detail before editing, show role multi-select, send `userRolList`, add row selection and batch delete, and refresh with page fallback.

- [ ] **Step 2: Implement role CRUD and permission tree**

Use backend role fields, add searchable pagination and row selection, implement create/edit/delete, and make the permission modal load `menuIds` plus the menu tree before calling `bindMenus`.

- [ ] **Step 3: Replace menu mock with backend tree CRUD**

Remove reset/mock imports and unsupported form fields. Edit `menuNm`, `permCd`, `path`, `prentId`, `sort`, `icon`, `typ`, `isDsp`, and `menuSource`; root uses `"0"`.

- [ ] **Step 4: Run frontend verification**

Run: `pnpm vitest run`

Run: `pnpm build`

Expected: tests and production build pass without TypeScript errors.

- [ ] **Step 5: Commit the three pages**

```bash
git add src/views/system/user src/views/system/role src/views/system/menu
git commit -m "feat: connect system management pages"
```

### Task 6: HTTP Documentation and Live Integration Verification

**Files:**
- Modify: `docs/test/system.http`

**Interfaces:**
- Consumes: gateway routes at `http://127.0.0.1:8866/system`.
- Produces: reproducible CRUD/binding request examples and a verified live flow.

- [ ] **Step 1: Add complete HTTP examples**

Document page/detail/save/delete for users, roles, and menus plus `menuIds`/`bindMenus`, using HTTP client variables for generated IDs.

- [ ] **Step 2: Build the backend and restart the system service if required**

Run: `mvn -pl nova-system -am -DskipTests package`

Start the produced system-service jar on the configured test port only if the running service does not yet expose the new routes.

- [ ] **Step 3: Execute a live CRUD/binding flow**

Create a unique temporary menu, role, and user; bind the menu to the role and the role to the user; query each detail/list response; update each record; then delete the user, role, and menu. Capture IDs from response/page queries rather than converting them to numbers.

- [ ] **Step 4: Confirm cleanup and rerun regression suites**

Confirm temporary names no longer appear in page/list responses, then run backend tests, frontend tests, frontend build, and `git diff --check` in both repositories.

- [ ] **Step 5: Commit HTTP documentation**

```bash
git add docs/test/system.http
git commit -m "docs: cover system management APIs"
```
