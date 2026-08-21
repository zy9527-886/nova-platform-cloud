package org.nova.platform.system.entity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * Auth login response.
 *
 * @author yyg
 */
@Getter
@Setter
@Schema(description = "auth login response")
public class AuthLoginVo {

    @Schema(description = "access token")
    private String accessToken;

    @Schema(description = "current auth user")
    private AuthUserVo authUser;
}
