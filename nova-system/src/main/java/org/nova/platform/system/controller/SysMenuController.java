package org.nova.platform.system.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.nova.platform.common.database.controller.BaseController;
import org.nova.platform.system.entity.SysMenu;
import org.nova.platform.system.service.SysMenuService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 银行用户菜单权限(SysMenu)表控制层
 *
 * @author yyg
 * @since 2026-08-13 16:54:35
 */
@RestController
@RequestMapping("/sysMenu")
@Tag(name = "银行用户菜单权限 控制层")
public class SysMenuController extends BaseController<SysMenu, SysMenuService> {
}

