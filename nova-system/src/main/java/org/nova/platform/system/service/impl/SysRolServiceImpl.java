package org.nova.platform.system.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.nova.platform.system.dao.SysRolDao;
import org.nova.platform.system.entity.SysRol;
import org.nova.platform.system.entity.vo.SysRolVo;
import org.nova.platform.system.service.SysRolService;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * 银行用户角色表(SysRol)表服务实现类
 *
 * @author yyg
 * @since 2026-08-13 16:52:46
 */
@Service("sysRolService")
public class SysRolServiceImpl extends ServiceImpl<SysRolDao, SysRol> implements SysRolService {


}

