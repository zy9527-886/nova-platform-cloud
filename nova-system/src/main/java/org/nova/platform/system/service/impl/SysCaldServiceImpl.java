package org.nova.platform.system.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.nova.platform.system.dao.SysCaldDao;
import org.nova.platform.system.entity.SysCald;
import org.nova.platform.system.service.SysCaldService;
import org.springframework.stereotype.Service;

/**
 * 日历信息(SysCald)表服务实现类
 *
 * @author yyg
 * @since 2026-08-13 16:55:15
 */
@Service("sysCaldService")
public class SysCaldServiceImpl extends ServiceImpl<SysCaldDao, SysCald> implements SysCaldService {

}

