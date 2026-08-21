package org.nova.platform.system.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.nova.platform.common.core.http.ResultUtils;
import org.nova.platform.common.database.controller.BaseController;
import org.nova.platform.system.entity.SysUser;
import org.nova.platform.system.entity.dto.SysUserDto;
import org.nova.platform.system.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;

/**
 * 银行用户表(SysUser)表控制层
 *
 * @author yyg
 * @since 2026-08-13 16:23:14
 */
@RestController
@RequestMapping("/sysUser")
@Tag(name = "银行用户表(SysUser)表控制层")
public class SysUserController {
    @Autowired
    private  SysUserService service;

    @Operation(description = "查询单条数据")
    @GetMapping("/getById/{id}")
    public Object getById(@PathVariable Serializable id){
        return ResultUtils.suc(service.getById(id));
    }

    @Operation(description = "新增或修改单条数据")
    @PostMapping("/saveOrUpdate")
    public Object saveOrUpdate(@RequestBody @Validated SysUserDto entity){
        return ResultUtils.suc(service.saveOrUpdate(entity));
    }

    @Operation(description = "删除单条数据")
    @DeleteMapping("/removeById/{id}")
    public Object removeById(@PathVariable Serializable id) {
        return ResultUtils.suc(service.removeById(id));
    }

    @Operation(description = "批量删除数据")
    @DeleteMapping("/removeByIds")
    public Object removeByIds(@RequestBody List<Serializable> idList) {
        return  ResultUtils.suc(service.removeByIds(idList));
    }
}

