package org.nova.platform.system.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.nova.platform.common.database.controller.BaseController;
import org.nova.platform.system.entity.SysRol;
import org.nova.platform.system.service.SysRolService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 银行用户角色表(SysRol)表控制层
 *
 * @author yyg
 * @since 2026-08-13 16:52:46
 */
@RestController
@RequestMapping("/sysRol")
@Tag(name = "银行用户角色表 控制层")
public class SysRolController  extends BaseController<SysRol, SysRolService>  {

}

