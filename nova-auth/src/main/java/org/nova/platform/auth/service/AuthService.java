package org.nova.platform.auth.service;

import org.nova.platform.system.entity.SysMenu;
import org.nova.platform.system.entity.dto.AuthLoginDto;
import org.nova.platform.system.entity.vo.AuthLoginVo;
import org.nova.platform.system.entity.vo.AuthMenuVo;
import org.nova.platform.system.entity.vo.AuthResourceVo;

import java.util.List;
import java.util.Map;

/**
 * Auth service.
 *
 * @author yyg
 */
public interface AuthService {

    AuthLoginVo login(AuthLoginDto loginDto);

    void logout();

    AuthResourceVo getCurrentResources();

    Map<String, List<String>> getRoleButtonPaths();

    void clearRoleButtonPaths();

    List<SysMenu> getCurrentCodes();

    List<AuthMenuVo> getCurrentMenus();
}
