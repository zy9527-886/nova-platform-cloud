package org.nova.platform.system.service;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.service.IService;
import org.nova.platform.common.database.page.PageQuery;
import org.nova.platform.system.entity.SysTsk;
import org.nova.platform.system.entity.dto.SysTskDto;

public interface SysTskService extends IService<SysTsk> {

    Page<SysTsk> selectPage(PageQuery<SysTsk, SysTskDto> pageQuery);
}
