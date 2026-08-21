package org.nova.platform.system.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 用户角色表(SysUserRol)表实体类
 *
 * @author yyg
 * @since 2026-08-13 16:46:18
 */

@Schema(description ="用户角色表")
@Getter
@Setter
@Accessors(chain = true)
public class SysUserRol  {
    @Schema(description ="用户id")      
    private String userId;

    @Schema(description ="角色id")      
    private String rolId;

}

