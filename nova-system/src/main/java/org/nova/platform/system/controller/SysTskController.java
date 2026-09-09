package org.nova.platform.system.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.nova.platform.common.core.http.ResultUtils;
import org.nova.platform.common.database.page.PageQuery;
import org.nova.platform.system.entity.SysTsk;
import org.nova.platform.system.entity.dto.SysTskDto;
import org.nova.platform.system.service.SysTskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.util.List;

@RestController
@RequestMapping("/sysTsk")
@Tag(name = "系统任务")
public class SysTskController {

    @Autowired
    private SysTskService service;

    @Operation(description = "任务分页查询")
    @PostMapping("/page")
    public Object page(@RequestBody PageQuery<SysTsk, SysTskDto> pageQuery) {
        return ResultUtils.suc(service.selectPage(pageQuery));
    }

    @Operation(description = "删除任务")
    @DeleteMapping("/removeById/{id}")
    public Object removeById(@PathVariable Serializable id) {
        return ResultUtils.suc(service.removeById(id));
    }

    @Operation(description = "批量删除任务")
    @DeleteMapping("/removeByIds")
    public Object removeByIds(@RequestBody List<Serializable> ids) {
        return ResultUtils.suc(service.removeByIds(ids));
    }
}
