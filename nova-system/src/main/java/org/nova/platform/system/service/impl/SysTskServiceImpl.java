package org.nova.platform.system.service.impl;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.nova.platform.common.database.page.PageQuery;
import org.nova.platform.system.dao.SysTskDao;
import org.nova.platform.system.entity.SysTsk;
import org.nova.platform.system.entity.dto.SysTskDto;
import org.nova.platform.system.service.SysTskService;
import org.springframework.stereotype.Service;

@Service("sysTskService")
public class SysTskServiceImpl extends ServiceImpl<SysTskDao, SysTsk> implements SysTskService {

    @Override
    public Page<SysTsk> selectPage(PageQuery<SysTsk, SysTskDto> pageQuery) {
        return mapper.selectPage(pageQuery.getPage(), pageQuery.getQuery());
    }
}
