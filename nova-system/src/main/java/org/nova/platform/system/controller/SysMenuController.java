package org.nova.platform.system.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.nova.platform.common.core.http.ResultUtils;
import org.nova.platform.common.database.controller.BaseController;
import org.nova.platform.system.entity.SysMenu;
import org.nova.platform.system.service.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;

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

    @Autowired
    private SysMenuService menuService;

    @Operation(description = "查询全部菜单")
    @GetMapping("/list")
    public Object list() {
        return ResultUtils.suc(menuService.listOrdered());
    }

    @Override
    @DeleteMapping("/removeById/{id}")
    public Object removeById(@PathVariable Serializable id) {
        return ResultUtils.suc(menuService.removeLeafById(id));
    }
}

