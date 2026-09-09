package org.nova.platform.system.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.nova.platform.common.core.http.ResultUtils;
import org.nova.platform.common.database.page.PageQuery;
import org.nova.platform.system.entity.SysOrg;
import org.nova.platform.system.entity.dto.SysOrgDto;
import org.nova.platform.system.service.SysOrgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;

/**
 * 机构信息表(SysOrg)表控制层
 *
 * @author yyg
 * @since 2026-08-13 16:53:26
 */
@RestController
@RequestMapping("/sysOrg")
@Tag(name = "机构信息表 控制层")
public class SysOrgController {

    @Autowired
    private SysOrgService service;

    @Operation(description = "机构分页查询")
    @PostMapping("/page")
    public Object page(@RequestBody PageQuery<SysOrg, SysOrgDto> pageQuery) {
        return ResultUtils.suc(service.selectPage(pageQuery));
    }

    @Operation(description = "机构树查询（Redis 缓存 30 分钟）")
    @GetMapping("/tree")
    public Object tree() {
        return ResultUtils.suc(service.getTree());
    }

    @Operation(description = "机构详情")
    @GetMapping("/getById/{id}")
    public Object getById(@PathVariable Serializable id) {
        return ResultUtils.suc(service.getById(id));
    }

    @Operation(description = "新增或修改机构")
    @PostMapping("/saveOrUpdate")
    public Object saveOrUpdate(@RequestBody @Validated SysOrg entity) {
        return ResultUtils.suc(service.saveOrUpdateOrg(entity));
    }

    @Operation(description = "删除机构")
    @DeleteMapping("/removeById/{id}")
    public Object removeById(@PathVariable Serializable id) {
        return ResultUtils.suc(service.removeOrgById(id));
    }

    @Operation(description = "批量删除机构")
    @DeleteMapping("/removeByIds")
    public Object removeByIds(@RequestBody List<Serializable> ids) {
        return ResultUtils.suc(service.removeOrgByIds(ids));
    }
}

