package org.nova.platform.system.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.nova.platform.system.dao.SysDictDao;
import org.nova.platform.system.entity.SysDict;
import org.nova.platform.system.service.SysDictService;
import org.springframework.stereotype.Service;

/**
 * 数据字典(SysDict)表服务实现类
 *
 * @author yyg
 * @since 2026-08-13 16:58:23
 */
@Service("sysDictService")
public class SysDictServiceImpl extends ServiceImpl<SysDictDao, SysDict> implements SysDictService {

}

