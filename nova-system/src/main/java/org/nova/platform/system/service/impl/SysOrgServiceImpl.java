package org.nova.platform.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.nova.platform.common.core.exception.BusinessException;
import org.nova.platform.common.database.page.PageQuery;
import org.nova.platform.common.database.utils.IdGenerate;
import org.nova.platform.system.dao.SysOrgDao;
import org.nova.platform.system.dao.SysUserDao;
import org.nova.platform.system.entity.SysOrg;
import org.nova.platform.system.entity.SysUser;
import org.nova.platform.system.entity.dto.SysOrgDto;
import org.nova.platform.system.entity.vo.SysOrgTreeVo;
import org.nova.platform.system.service.SysOrgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 机构信息表(SysOrg)表服务实现类
 *
 * @author yyg
 * @since 2026-08-13 16:53:26
 */
@Service("sysOrgService")
public class SysOrgServiceImpl extends ServiceImpl<SysOrgDao, SysOrg> implements SysOrgService {

    @Autowired
    private SysUserDao sysUserDao;

    @Override
    public Page<SysOrg> selectPage(PageQuery<SysOrg, SysOrgDto> pageQuery) {
        return mapper.selectPage(pageQuery.getPage(), pageQuery.getQuery());
    }

    @Override
    public List<SysOrgTreeVo> getTree() {
        List<SysOrg> organizations = new ArrayList<>(mapper.selectAll());
        organizations.sort(Comparator.comparing(SysOrg::getOrgCd, Comparator.nullsLast(String::compareTo)));

        Map<String, SysOrgTreeVo> nodesByCode = new LinkedHashMap<>();
        organizations.forEach(item -> nodesByCode.put(item.getOrgCd(), toTreeNode(item)));

        List<SysOrgTreeVo> roots = new ArrayList<>();
        organizations.forEach(item -> {
            SysOrgTreeVo node = nodesByCode.get(item.getOrgCd());
            SysOrgTreeVo parent = nodesByCode.get(item.getOrgPrentId());
            if (parent == null || parent == node) {
                roots.add(node);
            } else {
                parent.getChildren().add(node);
            }
        });
        return roots;
    }

    @Override
    @Transactional
    public boolean saveOrUpdateOrg(SysOrg entity) {
        if (entity == null || StringUtils.isBlank(entity.getOrgNm())) {
            throw new BusinessException("机构名称不能为空");
        }

        boolean inserted = StringUtils.isBlank(entity.getOrgId());
        int affected;
        if (inserted) {
            String parentCode = StringUtils.defaultIfBlank(entity.getOrgPrentId(), "0");
            entity.setOrgId(IdGenerate.getIdStr())
                    .setOrgPrentId(parentCode)
                    .setOrgCd(nextOrgCode(parentCode))
                    .setOrgLvCd(String.valueOf("0".equals(parentCode) ? 1 : parentCode.length() / 4 + 1))
                    .setOrgStus(StringUtils.defaultIfBlank(entity.getOrgStus(), "1"));
            affected = mapper.insertSelective(entity);
        } else {
            SysOrg existing = mapper.selectOneById(entity.getOrgId());
            if (existing == null) {
                throw new BusinessException("机构不存在");
            }
            entity.setOrgCd(existing.getOrgCd())
                    .setOrgPrentId(existing.getOrgPrentId())
                    .setOrgLvCd(existing.getOrgLvCd());
            affected = mapper.update(entity, true);
        }
        return affected > 0;
    }

    @Override
    @Transactional
    public boolean removeOrgById(Serializable id) {
        SysOrg organization = mapper.selectOneById(id);
        validateCanDelete(organization);
        boolean removed = mapper.deleteById(id) > 0;
        return removed;
    }

    @Override
    @Transactional
    public boolean removeOrgByIds(Collection<? extends Serializable> ids) {
        if (ids == null || ids.isEmpty()) {
            return false;
        }
        List<SysOrg> organizations = mapper.selectListByIds(ids);
        if (organizations.size() != ids.size()) {
            throw new BusinessException("包含不存在的机构");
        }
        organizations.forEach(this::validateCanDelete);
        boolean removed = mapper.deleteBatchByIds(ids) > 0;
        return removed;
    }

    private String nextOrgCode(String parentCode) {
        List<SysOrg> siblings = mapper.selectListByQuery(QueryWrapper.create()
                .where(SysOrg::getOrgPrentId).eq(parentCode));
        int next = siblings.stream()
                .map(SysOrg::getOrgCd)
                .filter(StringUtils::isNotBlank)
                .filter(code -> code.length() >= 4)
                .mapToInt(code -> Integer.parseInt(code.substring(code.length() - 4)))
                .max().orElse(0) + 1;
        if (next > 9999) {
            throw new BusinessException("同级机构数量已达到上限");
        }
        return ("0".equals(parentCode) ? "" : parentCode) + String.format("%04d", next);
    }

    private void validateCanDelete(SysOrg organization) {
        if (organization == null) {
            throw new BusinessException("机构不存在");
        }
        if (mapper.selectCountByQuery(QueryWrapper.create()
                .where(SysOrg::getOrgPrentId).eq(organization.getOrgCd())) > 0) {
            throw new BusinessException("该机构存在下级机构，不能删除");
        }
        if (sysUserDao.selectCountByQuery(QueryWrapper.create()
                .where(SysUser::getOrgCd).eq(organization.getOrgCd())) > 0) {
            throw new BusinessException("该机构已关联用户，不能删除");
        }
    }

    private SysOrgTreeVo toTreeNode(SysOrg entity) {
        return BeanUtil.copyProperties(entity, SysOrgTreeVo.class);
    }

}

