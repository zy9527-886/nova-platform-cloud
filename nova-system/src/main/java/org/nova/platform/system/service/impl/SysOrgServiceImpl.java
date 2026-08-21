package org.nova.platform.system.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.nova.platform.system.dao.SysOrgDao;
import org.nova.platform.system.entity.SysOrg;
import org.nova.platform.system.service.SysOrgService;
import org.springframework.stereotype.Service;

/**
 * 机构信息表(SysOrg)表服务实现类
 *
 * @author yyg
 * @since 2026-08-13 16:53:26
 */
@Service("sysOrgService")
public class SysOrgServiceImpl extends ServiceImpl<SysOrgDao, SysOrg> implements SysOrgService {

}

