package org.nova.platform.system.entity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.nova.platform.system.entity.SysMenu;

import java.util.ArrayList;
import java.util.List;

/**
 * Auth menu tree node.
 *
 * @author yyg
 */
@Getter
@Setter
@Schema(description = "登录用户菜单节点")
public class AuthMenuVo extends SysMenu {

    @Schema(description = "子菜单")
    private List<AuthMenuVo> children = new ArrayList<>();
}
