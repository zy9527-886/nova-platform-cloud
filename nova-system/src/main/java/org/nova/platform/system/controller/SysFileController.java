package org.nova.platform.system.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.nova.platform.common.database.controller.BaseController;
import org.nova.platform.system.entity.SysFile;
import org.nova.platform.system.service.SysFileService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 文件信息(SysFile)表控制层
 *
 * @author yyg
 * @since 2026-08-13 16:56:38
 */
@RestController
@RequestMapping("/sysFile")
@Tag(name = "文件信息 控制层")
public class SysFileController extends BaseController<SysFile, SysFileService> {

}

