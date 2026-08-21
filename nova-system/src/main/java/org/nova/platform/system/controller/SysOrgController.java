package org.nova.platform.system.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.nova.platform.common.database.controller.BaseController;
import org.nova.platform.system.entity.SysOrg;
import org.nova.platform.system.service.SysOrgService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 机构信息表(SysOrg)表控制层
 *
 * @author yyg
 * @since 2026-08-13 16:53:26
 */
@RestController
@RequestMapping("/sysOrg")
@Tag(name = "机构信息表 控制层")
public class SysOrgController extends BaseController<SysOrg, SysOrgService> {
}

