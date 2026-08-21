package org.nova.platform.system.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.nova.platform.system.dao.SysMenuDao;
import org.nova.platform.system.entity.SysMenu;
import org.nova.platform.system.service.SysMenuService;
import org.springframework.stereotype.Service;

/**
 * 银行用户菜单权限(SysMenu)表服务实现类
 *
 * @author yyg
 * @since 2026-08-13 16:54:35
 */
@Service("sysMenuService")
public class SysMenuServiceImpl extends ServiceImpl<SysMenuDao, SysMenu> implements SysMenuService {

}

