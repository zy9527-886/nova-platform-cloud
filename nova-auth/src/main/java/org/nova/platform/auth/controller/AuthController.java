package org.nova.platform.auth.controller;

import cn.dev33.satoken.exception.NotLoginException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.nova.platform.auth.service.AuthService;
import org.nova.platform.common.core.http.ResultUtils;
import org.nova.platform.common.core.http.StatusCode;
import org.nova.platform.common.database.config.RedisConfig;
import org.nova.platform.system.entity.dto.AuthLoginDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Auth controller.
 *
 * @author yyg
 */
@RestController
@RequestMapping("/system")
@Tag(name = "auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    @Operation(description = "login")
    public Object login(@RequestBody @Valid AuthLoginDto loginDto) {
        return ResultUtils.suc(authService.login(loginDto));
    }

    @PostMapping("/logout")
    @Operation(description = "logout")
    public Object logout() {
        authService.logout();
        return ResultUtils.suc(true);
    }

//    @GetMapping("/codes")
//    @Operation(description = "current user button permissions")
//    public Object codes() {
//        return ResultUtils.suc(authService.getCurrentCodes());
//    }
//
//    @GetMapping("/menus")
//    @Operation(description = "current user menus")
//    public Object menus() {
//        return ResultUtils.suc(authService.getCurrentMenus());
//    }

    @GetMapping("/resources")
    @Operation(description = "current user menus and button permissions")
    public Object resources() {
        return ResultUtils.suc(authService.getCurrentResources());
    }

    @GetMapping("/roleAndPaths")
    @Operation(description = "role button permission paths")
    @Cacheable(value = RedisConfig.CACHE_NAME_MINUTES_30, key = "'auth:role-button-paths'")
    public Object roleButtonPaths() {
        return ResultUtils.suc(authService.getRoleButtonPaths());
    }

    @PostMapping("/roleAndPaths/clear")
    @Operation(description = "clear role button permission path cache")
    @CacheEvict(value = RedisConfig.CACHE_NAME_MINUTES_30, key = "'auth:role-button-paths'")
    public Object clearRoleButtonPaths() {
        authService.clearRoleButtonPaths();
        return ResultUtils.suc(true);
    }

    @ExceptionHandler(NotLoginException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Object handleNotLogin(NotLoginException e) {
        return ResultUtils.error(StatusCode.LOGOUT.getCode(), StatusCode.LOGOUT.getMeg(), e.getMessage());
    }
}
