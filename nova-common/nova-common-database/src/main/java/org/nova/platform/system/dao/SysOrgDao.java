package org.nova.platform.system.dao;

import com.mybatisflex.core.BaseMapper;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import org.nova.platform.system.entity.SysOrg;
import org.nova.platform.system.entity.dto.SysOrgDto;

/**
 * 机构信息表(SysOrg)表数据库访问层
 *
 * @author yyg
 * @since 2026-08-13 16:49:18
 */
public interface SysOrgDao extends BaseMapper<SysOrg> {

    default Page<SysOrg> selectPage(Page<SysOrg> page, SysOrgDto query) {
        QueryWrapper wrapper = QueryWrapper.create().orderBy(SysOrg::getOrgCd, true);
        if (query != null) {
            wrapper.where(SysOrg::getOrgCd).likeRight(query.getOrgCd(), SysOrgDao::hasText)
                    .and(SysOrg::getOrgNm).like(query.getOrgNm(), SysOrgDao::hasText)
                    .and(SysOrg::getOrgStus).eq(query.getOrgStus(), SysOrgDao::hasText)
                    .and(SysOrg::getOrgCd).likeRight(query.getTreeOrgCd(), SysOrgDao::hasText);
        }
        return paginate(page, wrapper);
    }

    private static boolean hasText(String value) {
        return value != null && !value.isBlank();
    }

}

