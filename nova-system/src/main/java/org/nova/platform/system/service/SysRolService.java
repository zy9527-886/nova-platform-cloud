package org.nova.platform.system.service;

import com.mybatisflex.core.service.IService;
import org.nova.platform.system.entity.SysRol;
import org.nova.platform.system.entity.dto.SysRolMenuBindDto;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * 银行用户角色表(SysRol)表服务接口
 *
 * @author yyg
 * @since 2026-08-13 16:52:46
 */
public interface SysRolService extends IService<SysRol> {

    List<String> getMenuIds(String rolId);

    boolean bindMenus(SysRolMenuBindDto request);

    boolean removeByIdWithRelations(Serializable rolId);

    boolean removeByIdsWithRelations(Collection<? extends Serializable> rolIds);

}

