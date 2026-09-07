package org.nova.platform.system.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.mybatisflex.core.query.QueryWrapper;
import org.apache.commons.lang3.StringUtils;
import org.nova.platform.common.core.exception.BusinessException;
import org.nova.platform.system.dao.SysRolDao;
import org.nova.platform.system.dao.SysRolMenuDao;
import org.nova.platform.system.dao.SysUserRolDao;
import org.nova.platform.system.dao.SysMenuDao;
import org.nova.platform.system.entity.SysRol;
import org.nova.platform.system.entity.SysRolMenu;
import org.nova.platform.system.entity.SysUserRol;
import org.nova.platform.system.entity.dto.SysRolMenuBindDto;
import org.nova.platform.system.service.SysRolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;

/**
 * 银行用户角色表(SysRol)表服务实现类
 *
 * @author yyg
 * @since 2026-08-13 16:52:46
 */
@Service("sysRolService")
public class SysRolServiceImpl extends ServiceImpl<SysRolDao, SysRol> implements SysRolService {

    @Autowired
    private SysRolMenuDao sysRolMenuDao;

    @Autowired
    private SysUserRolDao sysUserRolDao;

    @Autowired
    private SysMenuDao sysMenuDao;

    @Override
    public List<String> getMenuIds(String rolId) {
        return sysRolMenuDao.selectListByQuery(
                        QueryWrapper.create().eq(SysRolMenu::getRolId, rolId))
                .stream()
                .map(SysRolMenu::getMenuId)
                .filter(Objects::nonNull)
                .distinct()
                .toList();
    }

    @Transactional
    @Override
    public boolean bindMenus(SysRolMenuBindDto request) {
        if (mapper.selectOneById(request.getRolId()) == null) {
            throw new BusinessException("角色不存在");
        }
        LinkedHashSet<String> menuIds = new LinkedHashSet<>();
        if (request.getMenuIds() != null) {
            request.getMenuIds().stream()
                    .filter(StringUtils::isNotBlank)
                    .forEach(menuIds::add);
        }
        if (!menuIds.isEmpty() && sysMenuDao.selectListByIds(menuIds).size() != menuIds.size()) {
            throw new BusinessException("包含不存在的菜单");
        }

        sysRolMenuDao.deleteByQuery(
                QueryWrapper.create().eq(SysRolMenu::getRolId, request.getRolId()));
        if (menuIds.isEmpty()) {
            return true;
        }
        List<SysRolMenu> rows = menuIds.stream()
                .map(menuId -> new SysRolMenu().setRolId(request.getRolId()).setMenuId(menuId))
                .toList();
        return sysRolMenuDao.insertBatchSelective(rows) > 0;
    }

    @Transactional
    @Override
    public boolean removeByIdWithRelations(Serializable rolId) {
        sysUserRolDao.deleteByQuery(QueryWrapper.create().eq(SysUserRol::getRolId, rolId));
        sysRolMenuDao.deleteByQuery(QueryWrapper.create().eq(SysRolMenu::getRolId, rolId));
        return mapper.deleteById(rolId) > 0;
    }

    @Transactional
    @Override
    public boolean removeByIdsWithRelations(Collection<? extends Serializable> rolIds) {
        if (rolIds == null || rolIds.isEmpty()) {
            return false;
        }
        sysUserRolDao.deleteByQuery(QueryWrapper.create().in(SysUserRol::getRolId, rolIds));
        sysRolMenuDao.deleteByQuery(QueryWrapper.create().in(SysRolMenu::getRolId, rolIds));
        return mapper.deleteBatchByIds(rolIds) > 0;
    }


}

