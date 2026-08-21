package org.nova.platform.system.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.nova.platform.system.dao.SysCfgDao;
import org.nova.platform.system.entity.SysCfg;
import org.nova.platform.system.service.SysCfgService;
import org.springframework.stereotype.Service;

/**
 * 系统配置信息(SysCfg)表服务实现类
 *
 * @author yyg
 * @since 2026-08-13 16:58:05
 */
@Service("sysCfgService")
public class SysCfgServiceImpl extends ServiceImpl<SysCfgDao, SysCfg> implements SysCfgService {

}

