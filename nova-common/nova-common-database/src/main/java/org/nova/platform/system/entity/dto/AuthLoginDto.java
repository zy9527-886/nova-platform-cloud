package org.nova.platform.system.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * Auth login request.
 *
 * @author yyg
 */
@Getter
@Setter
@Schema(description = "登录请求")
public class AuthLoginDto {

    @NotBlank
    @Schema(description = "登录账号，对应 SysUser.userNm")
    private String username;

    @NotBlank
    @Schema(description = "登录密码")
    private String password;
}
