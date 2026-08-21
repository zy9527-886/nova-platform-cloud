package org.nova.platform.system.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 角色菜单中间表(SysRolMenu)表实体类
 *
 * @author yyg
 * @since 2026-08-13 16:48:33
 */

@Schema(description ="角色菜单中间表")
@Getter
@Setter
@Accessors(chain = true)
public class SysRolMenu  {
    @Schema(description ="角色id")      
    private String rolId;

    @Schema(description ="菜单id")      
    private String menuId;

}

