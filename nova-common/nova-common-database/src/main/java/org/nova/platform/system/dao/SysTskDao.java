package org.nova.platform.system.dao;

import com.mybatisflex.core.BaseMapper;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import org.nova.platform.system.entity.SysTsk;
import org.nova.platform.system.entity.dto.SysTskDto;

public interface SysTskDao extends BaseMapper<SysTsk> {

    default Page<SysTsk> selectPage(Page<SysTsk> page, SysTskDto query) {
        QueryWrapper wrapper = QueryWrapper.create().orderBy(SysTsk::getCreTm, false);
        if (query != null) {
            wrapper.where(SysTsk::getTskTyp).likeRight(query.getTskTyp(), SysTskDao::hasText)
                    .and(SysTsk::getTskNm).like(query.getTskNm(), SysTskDao::hasText)
                    .and(SysTsk::getTskDt).eq(query.getTskDt(), SysTskDao::hasText)
                    .and(SysTsk::getStus).eq(query.getStus(), SysTskDao::hasText);
        }
        return paginate(page, wrapper);
    }

    private static boolean hasText(String value) {
        return value != null && !value.isBlank();
    }
}
