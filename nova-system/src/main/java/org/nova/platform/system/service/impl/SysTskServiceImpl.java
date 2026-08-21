package org.nova.platform.system.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.nova.platform.system.dao.SysTskDao;
import org.nova.platform.system.entity.SysTsk;
import org.nova.platform.system.service.SysTskService;
import org.springframework.stereotype.Service;

/**
 * 系统任务(SysTsk)表服务实现类
 *
 * @author yyg
 * @since 2026-08-13 16:51:27
 */
@Service("sysTskService")
public class SysTskServiceImpl extends ServiceImpl<SysTskDao, SysTsk> implements SysTskService {

}

