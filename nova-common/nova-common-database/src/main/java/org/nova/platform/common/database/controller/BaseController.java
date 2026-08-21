package org.nova.platform.common.database.controller;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.service.IService;
import io.swagger.v3.oas.annotations.Operation;
import org.nova.platform.common.core.http.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 通用表控制层
 *
 * @author: yyg
 * @since: 2026/8/17 17:03
 */
@RestController
public abstract class BaseController<T, S extends IService<T>> {

    @Autowired
    private  S service;

    @Value("${base.config.batch-num:100}")
    private int num;

    @Operation(description = "分页查询")
    @PostMapping("/page")
    public Object page(@RequestBody Page<T> page){
        return ResultUtils.suc(service.page(page));
    }

    @Operation(description = "查询单条数据")
    @GetMapping("/getById/{id}")
    public Object getById(@PathVariable Serializable id){
        return ResultUtils.suc(service.getById(id));
    }

    @Operation(description = "查询")
    @PostMapping("/listByMap")
    public Object list(@RequestBody Map map ){
        return ResultUtils.suc(service.listByMap(map));
    }

    @Operation(description = "新增或修改单条数据")
    @PostMapping("/saveOrUpdate")
    public Object saveOrUpdate(@RequestBody @Validated T entity){
        return ResultUtils.suc(service.saveOrUpdate(entity));
    }

    @Operation(description = "新增或修批量数据")
    @PostMapping("/saveOrUpdateBatch")
    public Object saveOrUpdateBatch(@RequestBody List<T> entity){
        return ResultUtils.suc(service.saveOrUpdateBatch(entity,num));
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
