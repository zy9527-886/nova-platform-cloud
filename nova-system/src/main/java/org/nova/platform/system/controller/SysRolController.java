package org.nova.platform.system.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.nova.platform.common.core.http.ResultUtils;
import org.nova.platform.common.database.controller.BaseController;
import org.nova.platform.system.entity.SysRol;
import org.nova.platform.system.entity.dto.SysRolMenuBindDto;
import org.nova.platform.system.service.SysRolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;

/**
 * 银行用户角色表(SysRol)表控制层
 *
 * @author yyg
 * @since 2026-08-13 16:52:46
 */
@RestController
@RequestMapping("/sysRol")
@Tag(name = "银行用户角色表 控制层")
public class SysRolController  extends BaseController<SysRol, SysRolService>  {

    @Autowired
    private SysRolService roleService;

    @Operation(description = "查询全部角色")
    @GetMapping("/list")
    public Object list() {
        return ResultUtils.suc(roleService.list());
    }

    @Operation(description = "查询角色已绑定菜单")
    @GetMapping("/menuIds/{rolId}")
    public Object getMenuIds(@PathVariable String rolId) {
        return ResultUtils.suc(roleService.getMenuIds(rolId));
    }

    @Operation(description = "保存角色菜单绑定")
    @PostMapping("/bindMenus")
    public Object bindMenus(@RequestBody @Validated SysRolMenuBindDto request) {
        return ResultUtils.suc(roleService.bindMenus(request));
    }

    @Override
    @DeleteMapping("/removeById/{id}")
    public Object removeById(@PathVariable Serializable id) {
        return ResultUtils.suc(roleService.removeByIdWithRelations(id));
    }

    @Override
    @DeleteMapping("/removeByIds")
    public Object removeByIds(@RequestBody List<Serializable> idList) {
        return ResultUtils.suc(roleService.removeByIdsWithRelations(idList));
    }

}

