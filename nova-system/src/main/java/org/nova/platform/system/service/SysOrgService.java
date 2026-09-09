package org.nova.platform.system.service;

import com.mybatisflex.core.service.IService;
import com.mybatisflex.core.paginate.Page;
import org.nova.platform.common.database.page.PageQuery;
import org.nova.platform.system.entity.SysOrg;
import org.nova.platform.system.entity.dto.SysOrgDto;
import org.nova.platform.system.entity.vo.SysOrgTreeVo;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * 机构信息表(SysOrg)表服务接口
 *
 * @author yyg
 * @since 2026-08-13 16:53:26
 */
public interface SysOrgService extends IService<SysOrg> {

    Page<SysOrg> selectPage(PageQuery<SysOrg, SysOrgDto> pageQuery);

    List<SysOrgTreeVo> getTree();

    boolean saveOrUpdateOrg(SysOrg entity);

    boolean removeOrgById(Serializable id);

    boolean removeOrgByIds(Collection<? extends Serializable> ids);

}

