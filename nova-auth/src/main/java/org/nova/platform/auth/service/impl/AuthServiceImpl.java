package org.nova.platform.auth.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.stp.SaLoginConfig;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.SM2;
import com.alibaba.fastjson2.JSON;
import com.mybatisflex.core.query.QueryWrapper;
import org.bouncycastle.crypto.engines.SM2Engine;
import org.nova.platform.auth.service.AuthService;
import org.nova.platform.common.core.http.ResultUtils;
import org.nova.platform.common.core.http.StatusCode;
import org.nova.platform.common.database.config.RedisConfig;
import org.nova.platform.system.dao.SysMenuDao;
import org.nova.platform.system.dao.SysRolDao;
import org.nova.platform.system.dao.SysUserDao;
import org.nova.platform.system.dao.SysUserRolDao;
import org.nova.platform.system.entity.SysMenu;
import org.nova.platform.system.entity.SysRol;
import org.nova.platform.system.entity.SysUser;
import org.nova.platform.system.entity.SysUserRol;
import org.nova.platform.system.entity.dto.AuthLoginDto;
import org.nova.platform.system.entity.vo.AuthLoginVo;
import org.nova.platform.system.entity.vo.AuthMenuVo;
import org.nova.platform.system.entity.vo.AuthResourceVo;
import org.nova.platform.system.entity.vo.AuthUserVo;
import org.nova.platform.system.enums.SystemEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Auth service implementation.
 *
 * @author yyg
 */
@Service
public class AuthServiceImpl implements AuthService {

    private static final String AUTH_USER_KEY = "authUser";

    @Autowired
    private SysUserDao sysUserDao;

    @Autowired
    private SysUserRolDao sysUserRolDao;

    @Autowired
    private SysRolDao sysRolDao;

    @Autowired
    private SysMenuDao sysMenuDao;

    @Value("${base.security.sm.private-key:2064592f174e4fab950a3b3ef6b20cbe696b3cf319cfd6a2fcb1b1f8a01cf933}")
    private String  privateKey;
    @Value("${base.security.sm.public-key:046d96396327a558afddde5ab9e6562025923bd6491958a14f451bb5d8a986b5c10ed36ee2891ee969cef686aefe8b925975bf2cfcd6c398618a8b02ca454daf56}")
    private String  publicKey;

    @Override
    public AuthLoginVo login(AuthLoginDto loginDto) {
        SysUser user = sysUserDao.selectOneByQuery(
                QueryWrapper.create()
                        .select(
                                "user_id",
                                "user_nm",
                                "rmk",
                                "pwd",
                                "id_typ",
                                "id_no",
                                "real_nm",
                                "tel",
                                "org_cd",
                                "stus",
                                "pwd_err_tms",
                                "pwd_chg_tm",
                                "lst_lgn_tm",
                                "lock_tm"
                        )
                        .eq(SysUser::getUserNm, loginDto.getUsername())
        );
        if (user == null) {
            ResultUtils.throwServiceException(StatusCode.USER_NOT_EXIST);
        }
        //sm2 decrypt
        SM2 sm2 = SmUtil.sm2(privateKey, null);
        sm2.setMode(SM2Engine.Mode.C1C3C2);
        String result = StrUtil.utf8Str(sm2.decrypt(loginDto.getPassword(), KeyType.PrivateKey));
        if (!Objects.equals(user.getPwd(), result)) {
            ResultUtils.throwServiceException(StatusCode.PWD_ERROR);
        }
        if (Objects.equals(SystemEnum.EnableStatus.DISABLED.getCode(), user.getStus())) {
            ResultUtils.throwServiceException(StatusCode.USER_LOCKED);
        }

        AuthUserVo authUser = buildAuthUser(user);
        StpUtil.login(
                user.getUserId(),
                SaLoginConfig.setExtra(AUTH_USER_KEY, JSON.toJSONString(authUser))
        );

        AuthLoginVo loginVo = new AuthLoginVo();
        loginVo.setAccessToken(StpUtil.getTokenValue());
        loginVo.setAuthUser(authUser);
        return loginVo;
    }

    @Override
    public void logout() {
        if (StpUtil.isLogin()) {
            StpUtil.logout();
        }
    }

    @Override
    public AuthResourceVo getCurrentResources() {
        List<SysMenu> userMenus = getUserMenus(StpUtil.getLoginIdAsString());
        Map<String, List<SysMenu>> menuGroup = userMenus.stream()
                .filter(menu -> menu.getTyp() != null)
                .collect(Collectors.groupingBy(SysMenu::getTyp));

        List<SysMenu> codes = menuGroup.getOrDefault(SystemEnum.MenuType.BUTTON.getCode(), new ArrayList<>()).stream()
                .filter(menu -> menu.getPermCd() != null && !menu.getPermCd().isBlank())
                .sorted(Comparator.comparing(SysMenu::getSort, Comparator.nullsLast(Integer::compareTo)))
                .collect(Collectors.toList());

        List<AuthMenuVo> menus = menuGroup.getOrDefault(SystemEnum.MenuType.MENU.getCode(), new ArrayList<>()).stream()
                .sorted(Comparator.comparing(SysMenu::getSort, Comparator.nullsLast(Integer::compareTo)))
                .map(menu -> BeanUtil.copyProperties(menu, AuthMenuVo.class))
                .collect(Collectors.toList());

        AuthResourceVo resourceVo = new AuthResourceVo();
        resourceVo.setCodes(codes);
        resourceVo.setMenus(buildMenuTree(menus));
        return resourceVo;
    }

    @Override
    public Map<String, List<String>> getRoleButtonPaths() {
        return sysMenuDao.selectRoleButtonPaths(SystemEnum.MenuType.BUTTON.getCode()).stream()
                .filter(row -> row.get("roleId") != null && row.get("path") != null)
                .collect(Collectors.groupingBy(
                        row -> row.get("roleId").toString(),
                        LinkedHashMap::new,
                        Collectors.mapping(row -> row.get("path").toString(), Collectors.toList())
                ));
    }

    @Override
    public void clearRoleButtonPaths() {
    }

    @Override
    public List<SysMenu> getCurrentCodes() {
        return getCurrentResources().getCodes();
    }

    @Override
    public List<AuthMenuVo> getCurrentMenus() {
        return getCurrentResources().getMenus();
    }

    private AuthUserVo buildAuthUser(SysUser user) {
        AuthUserVo authUser = new AuthUserVo();
        authUser.setUserId(user.getUserId());
        authUser.setUserNm(user.getUserNm());
        authUser.setRmk(user.getRmk());
        authUser.setIdTyp(user.getIdTyp());
        authUser.setIdNo(user.getIdNo());
        authUser.setRealNm(user.getRealNm());
        authUser.setTel(user.getTel());
        authUser.setOrgCd(user.getOrgCd());
        authUser.setStus(user.getStus());
        authUser.setPwdErrTms(user.getPwdErrTms());
        authUser.setPwdChgTm(user.getPwdChgTm());
        authUser.setLstLgnTm(user.getLstLgnTm());
        authUser.setLockTm(user.getLockTm());
        authUser.setRoles(getUserRoles(user.getUserId()));
        return authUser;
    }

    private List<SysRol> getUserRoles(String userId) {
        List<SysUserRol> userRoles = sysUserRolDao.selectListByQuery(
                QueryWrapper.create().eq(SysUserRol::getUserId, userId)
        );
        if (CollUtil.isEmpty(userRoles)) {
            return new ArrayList<>();
        }

        List<String> roleIds = userRoles.stream()
                .map(SysUserRol::getRolId)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());
        if (CollUtil.isEmpty(roleIds)) {
            return new ArrayList<>();
        }

        return sysRolDao.selectListByQuery(
                QueryWrapper.create()
                        .select(SysRol::getRolId, SysRol::getRolNm,SysRol::getRolCd)
                        .in(SysRol::getRolId, roleIds)
        );
    }

    private List<SysMenu> getUserMenus(String userId) {
        return sysMenuDao.selectUserResources(userId);
    }

    private List<AuthMenuVo> buildMenuTree(List<AuthMenuVo> menus) {
        Map<String, AuthMenuVo> menuMap = menus.stream()
                .filter(menu -> menu.getMenuId() != null)
                .collect(Collectors.toMap(
                        AuthMenuVo::getMenuId,
                        Function.identity(),
                        (left, right) -> left,
                        LinkedHashMap::new
                ));
        Set<String> menuIds = menuMap.keySet();
        List<AuthMenuVo> roots = new ArrayList<>();

        for (AuthMenuVo menu : menuMap.values()) {
            String parentId = menu.getPrentId();
            if (parentId == null || parentId.isBlank() || "0".equals(parentId) || !menuIds.contains(parentId)) {
                roots.add(menu);
                continue;
            }
            AuthMenuVo parent = menuMap.get(parentId);
            parent.getChildren().add(menu);
        }

        sortMenuTree(roots);
        return roots;
    }

    private void sortMenuTree(List<AuthMenuVo> menus) {
        menus.sort(Comparator.comparing(AuthMenuVo::getSort, Comparator.nullsLast(Integer::compareTo)));
        for (AuthMenuVo menu : menus) {
            sortMenuTree(menu.getChildren());
        }
    }
}
