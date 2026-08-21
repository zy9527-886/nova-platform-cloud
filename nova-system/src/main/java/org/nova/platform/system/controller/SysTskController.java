package org.nova.platform.system.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.nova.platform.common.database.controller.BaseController;
import org.nova.platform.system.entity.SysTsk;
import org.nova.platform.system.service.SysTskService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统任务(SysTsk)表控制层
 *
 * @author yyg
 * @since 2026-08-13 16:51:25
 */
@RestController
@RequestMapping("/sysTsk")
@Tag(name = "系统任务 控制层")
public class SysTskController  extends BaseController<SysTsk, SysTskService>  {

}

