package org.nova.platform.system.entity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.nova.platform.system.entity.SysMenu;

import java.util.ArrayList;
import java.util.List;

/**
 * Current auth resources.
 *
 * @author yyg
 */
@Getter
@Setter
@Schema(description = "current auth resources")
public class AuthResourceVo {

    @Schema(description = "button permissions")
    private List<SysMenu> codes = new ArrayList<>();

    @Schema(description = "menu tree")
    private List<AuthMenuVo> menus = new ArrayList<>();
}
