package org.nova.platform.system.service;

import com.mybatisflex.core.service.IService;
import org.nova.platform.system.entity.SysMenu;

import java.io.Serializable;
import java.util.List;

/**
 * 银行用户菜单权限(SysMenu)表服务接口
 *
 * @author yyg
 * @since 2026-08-13 16:54:36
 */
public interface SysMenuService extends IService<SysMenu> {

    List<SysMenu> listOrdered();

    boolean removeLeafById(Serializable menuId);

}

