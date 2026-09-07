package org.nova.platform.system.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.mybatisflex.core.query.QueryWrapper;
import org.nova.platform.common.core.exception.BusinessException;
import org.nova.platform.system.dao.SysMenuDao;
import org.nova.platform.system.dao.SysRolMenuDao;
import org.nova.platform.system.entity.SysMenu;
import org.nova.platform.system.entity.SysRolMenu;
import org.nova.platform.system.service.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;

/**
 * 银行用户菜单权限(SysMenu)表服务实现类
 *
 * @author yyg
 * @since 2026-08-13 16:54:35
 */
@Service("sysMenuService")
public class SysMenuServiceImpl extends ServiceImpl<SysMenuDao, SysMenu> implements SysMenuService {

    @Autowired
    private SysRolMenuDao sysRolMenuDao;

    @Override
    public List<SysMenu> listOrdered() {
        return mapper.selectAll().stream()
                .sorted(Comparator.comparing(SysMenu::getSort,
                                Comparator.nullsLast(Integer::compareTo))
                        .thenComparing(SysMenu::getMenuId,
                                Comparator.nullsLast(String::compareTo)))
                .toList();
    }

    @Transactional
    @Override
    public boolean removeLeafById(Serializable menuId) {
        long childCount = mapper.selectCountByQuery(
                QueryWrapper.create().eq(SysMenu::getPrentId, menuId));
        if (childCount > 0) {
            throw new BusinessException("请先删除子菜单");
        }
        sysRolMenuDao.deleteByQuery(QueryWrapper.create().eq(SysRolMenu::getMenuId, menuId));
        return mapper.deleteById(menuId) > 0;
    }

}

