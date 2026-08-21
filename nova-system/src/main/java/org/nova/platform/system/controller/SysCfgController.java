package org.nova.platform.system.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.nova.platform.common.database.controller.BaseController;
import org.nova.platform.system.entity.SysCfg;
import org.nova.platform.system.service.SysCfgService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统配置信息(SysCfg)表控制层
 *
 * @author yyg
 * @since 2026-08-13 16:58:05
 */
@RestController
@RequestMapping("/sysCfg")
@Tag(name = "系统配置信息 控制层")
public class SysCfgController  extends BaseController<SysCfg, SysCfgService>  {
}

