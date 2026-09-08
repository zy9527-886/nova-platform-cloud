INSERT INTO sys_menu (
    menu_id, menu_nm, perm_cd, path, prent_id, sort, icon, typ, is_dsp,
    cre_per, cre_tm, updt_per, updt_tm, menu_source
)
SELECT
    menu_id, menu_nm, perm_cd, path, prent_id, sort, icon, typ, is_dsp,
    'system-init', CURRENT_TIMESTAMP, 'system-init', CURRENT_TIMESTAMP, menu_source
FROM (
    VALUES
        ('300000000000000001', '系统管理', NULL, '/system', '0', 1, 'SettingOutlined', '1', 1, 'BasicLayout'),
        ('300000000000000002', '用户管理', NULL, '/system/user', '300000000000000001', 10, 'UserOutlined', '1', 1, '/system/user/list'),
        ('300000000000000003', '角色管理', NULL, '/system/role', '300000000000000001', 20, 'TeamOutlined', '1', 1, '/system/role/list'),
        ('300000000000000004', '菜单管理', NULL, '/system/menu', '300000000000000001', 30, 'MenuOutlined', '1', 1, '/system/menu/list'),
        ('300000000000000005', '组织管理', NULL, '/system/org', '300000000000000001', 40, 'ApartmentOutlined', '1', 1, '/system/org/list'),
        ('300000000000000006', '字典管理', NULL, '/system/dict', '300000000000000001', 50, 'BookOutlined', '1', 1, '/system/dict/list'),
        ('300000000000000007', '系统配置', NULL, '/system/config', '300000000000000001', 60, 'ControlOutlined', '1', 1, '/system/config/list'),
        ('300000000000000008', '日历管理', NULL, '/system/calendar', '300000000000000001', 70, 'CalendarOutlined', '1', 1, '/system/calendar/list'),
        ('300000000000000009', '文件管理', NULL, '/system/file', '300000000000000001', 80, 'FolderOpenOutlined', '1', 1, '/system/file/list'),
        ('300000000000000010', '任务管理', NULL, '/system/task', '300000000000000001', 90, 'ScheduleOutlined', '1', 1, '/system/task/list'),

        ('300000000000001001', '用户分页查询', 'system:user:page', '/sysUser/page', '300000000000000002', 101, NULL, '0', 0, NULL),
        ('300000000000001002', '用户详情', 'system:user:detail', '/sysUser/getById/{id}', '300000000000000002', 102, NULL, '0', 0, NULL),
        ('300000000000001003', '用户新增修改', 'system:user:save', '/sysUser/saveOrUpdate', '300000000000000002', 103, NULL, '0', 0, NULL),
        ('300000000000001004', '用户删除', 'system:user:delete', '/sysUser/removeById/{id}', '300000000000000002', 104, NULL, '0', 0, NULL),
        ('300000000000001005', '用户批量删除', 'system:user:deleteBatch', '/sysUser/removeByIds', '300000000000000002', 105, NULL, '0', 0, NULL),

        ('300000000000002001', '组织分页查询', 'system:org:page', '/sysOrg/page', '300000000000000005', 201, NULL, '0', 0, NULL),
        ('300000000000002002', '组织新增修改', 'system:org:save', '/sysOrg/saveOrUpdate', '300000000000000005', 202, NULL, '0', 0, NULL),
        ('300000000000002003', '组织详情', 'system:org:detail', '/sysOrg/getById/{id}', '300000000000000005', 203, NULL, '0', 0, NULL),
        ('300000000000002004', '组织条件查询', 'system:org:list', '/sysOrg/listByMap', '300000000000000005', 204, NULL, '0', 0, NULL),
        ('300000000000002005', '组织批量保存', 'system:org:saveBatch', '/sysOrg/saveOrUpdateBatch', '300000000000000005', 205, NULL, '0', 0, NULL),
        ('300000000000002006', '组织删除', 'system:org:delete', '/sysOrg/removeById/{id}', '300000000000000005', 206, NULL, '0', 0, NULL),
        ('300000000000002007', '组织批量删除', 'system:org:deleteBatch', '/sysOrg/removeByIds', '300000000000000005', 207, NULL, '0', 0, NULL),

        ('300000000000003001', '角色分页查询', 'system:role:page', '/sysRol/page', '300000000000000003', 301, NULL, '0', 0, NULL),
        ('300000000000003002', '角色新增修改', 'system:role:save', '/sysRol/saveOrUpdate', '300000000000000003', 302, NULL, '0', 0, NULL),
        ('300000000000003003', '角色详情', 'system:role:detail', '/sysRol/getById/{id}', '300000000000000003', 303, NULL, '0', 0, NULL),
        ('300000000000003004', '角色列表', 'system:role:list', '/sysRol/list', '300000000000000003', 304, NULL, '0', 0, NULL),
        ('300000000000003005', '角色条件查询', 'system:role:listByMap', '/sysRol/listByMap', '300000000000000003', 305, NULL, '0', 0, NULL),
        ('300000000000003006', '角色批量保存', 'system:role:saveBatch', '/sysRol/saveOrUpdateBatch', '300000000000000003', 306, NULL, '0', 0, NULL),
        ('300000000000003007', '角色删除', 'system:role:delete', '/sysRol/removeById/{id}', '300000000000000003', 307, NULL, '0', 0, NULL),
        ('300000000000003008', '角色批量删除', 'system:role:deleteBatch', '/sysRol/removeByIds', '300000000000000003', 308, NULL, '0', 0, NULL),
        ('300000000000003009', '查询角色菜单', 'system:role:menuIds', '/sysRol/menuIds/{rolId}', '300000000000000003', 309, NULL, '0', 0, NULL),
        ('300000000000003010', '分配角色权限', 'system:role:bindMenus', '/sysRol/bindMenus', '300000000000000003', 310, NULL, '0', 0, NULL),

        ('300000000000004001', '菜单分页查询', 'system:menu:page', '/sysMenu/page', '300000000000000004', 401, NULL, '0', 0, NULL),
        ('300000000000004002', '菜单新增修改', 'system:menu:save', '/sysMenu/saveOrUpdate', '300000000000000004', 402, NULL, '0', 0, NULL),
        ('300000000000004003', '菜单树列表', 'system:menu:list', '/sysMenu/list', '300000000000000004', 403, NULL, '0', 0, NULL),
        ('300000000000004004', '菜单详情', 'system:menu:detail', '/sysMenu/getById/{id}', '300000000000000004', 404, NULL, '0', 0, NULL),
        ('300000000000004005', '菜单条件查询', 'system:menu:listByMap', '/sysMenu/listByMap', '300000000000000004', 405, NULL, '0', 0, NULL),
        ('300000000000004006', '菜单批量保存', 'system:menu:saveBatch', '/sysMenu/saveOrUpdateBatch', '300000000000000004', 406, NULL, '0', 0, NULL),
        ('300000000000004007', '菜单删除', 'system:menu:delete', '/sysMenu/removeById/{id}', '300000000000000004', 407, NULL, '0', 0, NULL),
        ('300000000000004008', '菜单批量删除', 'system:menu:deleteBatch', '/sysMenu/removeByIds', '300000000000000004', 408, NULL, '0', 0, NULL),

        ('300000000000005001', '字典分页查询', 'system:dict:page', '/sysDict/page', '300000000000000006', 501, NULL, '0', 0, NULL),
        ('300000000000005002', '字典详情', 'system:dict:detail', '/sysDict/getById/{id}', '300000000000000006', 502, NULL, '0', 0, NULL),
        ('300000000000005003', '字典条件查询', 'system:dict:list', '/sysDict/listByMap', '300000000000000006', 503, NULL, '0', 0, NULL),
        ('300000000000005004', '字典新增修改', 'system:dict:save', '/sysDict/saveOrUpdate', '300000000000000006', 504, NULL, '0', 0, NULL),
        ('300000000000005005', '字典批量保存', 'system:dict:saveBatch', '/sysDict/saveOrUpdateBatch', '300000000000000006', 505, NULL, '0', 0, NULL),
        ('300000000000005006', '字典删除', 'system:dict:delete', '/sysDict/removeById/{id}', '300000000000000006', 506, NULL, '0', 0, NULL),
        ('300000000000005007', '字典批量删除', 'system:dict:deleteBatch', '/sysDict/removeByIds', '300000000000000006', 507, NULL, '0', 0, NULL),

        ('300000000000006001', '配置分页查询', 'system:config:page', '/sysCfg/page', '300000000000000007', 601, NULL, '0', 0, NULL),
        ('300000000000006002', '配置详情', 'system:config:detail', '/sysCfg/getById/{id}', '300000000000000007', 602, NULL, '0', 0, NULL),
        ('300000000000006003', '配置条件查询', 'system:config:list', '/sysCfg/listByMap', '300000000000000007', 603, NULL, '0', 0, NULL),
        ('300000000000006004', '配置新增修改', 'system:config:save', '/sysCfg/saveOrUpdate', '300000000000000007', 604, NULL, '0', 0, NULL),
        ('300000000000006005', '配置批量保存', 'system:config:saveBatch', '/sysCfg/saveOrUpdateBatch', '300000000000000007', 605, NULL, '0', 0, NULL),
        ('300000000000006006', '配置删除', 'system:config:delete', '/sysCfg/removeById/{id}', '300000000000000007', 606, NULL, '0', 0, NULL),
        ('300000000000006007', '配置批量删除', 'system:config:deleteBatch', '/sysCfg/removeByIds', '300000000000000007', 607, NULL, '0', 0, NULL),

        ('300000000000007001', '日历分页查询', 'system:calendar:page', '/sysCald/page', '300000000000000008', 701, NULL, '0', 0, NULL),
        ('300000000000007002', '日历详情', 'system:calendar:detail', '/sysCald/getById/{id}', '300000000000000008', 702, NULL, '0', 0, NULL),
        ('300000000000007003', '日历条件查询', 'system:calendar:list', '/sysCald/listByMap', '300000000000000008', 703, NULL, '0', 0, NULL),
        ('300000000000007004', '日历新增修改', 'system:calendar:save', '/sysCald/saveOrUpdate', '300000000000000008', 704, NULL, '0', 0, NULL),
        ('300000000000007005', '日历批量保存', 'system:calendar:saveBatch', '/sysCald/saveOrUpdateBatch', '300000000000000008', 705, NULL, '0', 0, NULL),
        ('300000000000007006', '日历删除', 'system:calendar:delete', '/sysCald/removeById/{id}', '300000000000000008', 706, NULL, '0', 0, NULL),
        ('300000000000007007', '日历批量删除', 'system:calendar:deleteBatch', '/sysCald/removeByIds', '300000000000000008', 707, NULL, '0', 0, NULL),

        ('300000000000008001', '文件分页查询', 'system:file:page', '/sysFile/page', '300000000000000009', 801, NULL, '0', 0, NULL),
        ('300000000000008002', '文件详情', 'system:file:detail', '/sysFile/getById/{id}', '300000000000000009', 802, NULL, '0', 0, NULL),
        ('300000000000008003', '文件条件查询', 'system:file:list', '/sysFile/listByMap', '300000000000000009', 803, NULL, '0', 0, NULL),
        ('300000000000008004', '文件新增修改', 'system:file:save', '/sysFile/saveOrUpdate', '300000000000000009', 804, NULL, '0', 0, NULL),
        ('300000000000008005', '文件批量保存', 'system:file:saveBatch', '/sysFile/saveOrUpdateBatch', '300000000000000009', 805, NULL, '0', 0, NULL),
        ('300000000000008006', '文件删除', 'system:file:delete', '/sysFile/removeById/{id}', '300000000000000009', 806, NULL, '0', 0, NULL),
        ('300000000000008007', '文件批量删除', 'system:file:deleteBatch', '/sysFile/removeByIds', '300000000000000009', 807, NULL, '0', 0, NULL),

        ('300000000000009001', '任务分页查询', 'system:task:page', '/sysTsk/page', '300000000000000010', 901, NULL, '0', 0, NULL),
        ('300000000000009002', '任务详情', 'system:task:detail', '/sysTsk/getById/{id}', '300000000000000010', 902, NULL, '0', 0, NULL),
        ('300000000000009003', '任务条件查询', 'system:task:list', '/sysTsk/listByMap', '300000000000000010', 903, NULL, '0', 0, NULL),
        ('300000000000009004', '任务新增修改', 'system:task:save', '/sysTsk/saveOrUpdate', '300000000000000010', 904, NULL, '0', 0, NULL),
        ('300000000000009005', '任务批量保存', 'system:task:saveBatch', '/sysTsk/saveOrUpdateBatch', '300000000000000010', 905, NULL, '0', 0, NULL),
        ('300000000000009006', '任务删除', 'system:task:delete', '/sysTsk/removeById/{id}', '300000000000000010', 906, NULL, '0', 0, NULL),
        ('300000000000009007', '任务批量删除', 'system:task:deleteBatch', '/sysTsk/removeByIds', '300000000000000010', 907, NULL, '0', 0, NULL)
) AS menu_seed (
    menu_id, menu_nm, perm_cd, path, prent_id, sort, icon, typ, is_dsp, menu_source
);

INSERT INTO sys_rol_menu (rol_id, menu_id)
SELECT '200000000000000001', menu_id
FROM sys_menu
WHERE menu_id LIKE '3000000000000%';
