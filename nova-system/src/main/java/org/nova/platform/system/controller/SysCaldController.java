package org.nova.platform.system.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.nova.platform.common.database.controller.BaseController;
import org.nova.platform.system.entity.SysCald;
import org.nova.platform.system.service.SysCaldService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 日历信息(SysCald)表控制层
 *
 * @author yyg
 * @since 2026-08-13 16:55:15
 */
@RestController
@RequestMapping("/sysCald")
@Tag(name = "日历信息 控制层")
public class SysCaldController  extends BaseController<SysCald, SysCaldService>  {
}

