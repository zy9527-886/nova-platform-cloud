package org.nova.platform.system.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.nova.platform.common.database.controller.BaseController;
import org.nova.platform.system.entity.SysDict;
import org.nova.platform.system.service.SysDictService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 数据字典(SysDict)表控制层
 *
 * @author yyg
 * @since 2026-08-13 16:58:20
 */
@RestController
@RequestMapping("/sysDict")
@Tag(name = "数据字典 控制层")
public class SysDictController extends BaseController<SysDict, SysDictService> {

}

