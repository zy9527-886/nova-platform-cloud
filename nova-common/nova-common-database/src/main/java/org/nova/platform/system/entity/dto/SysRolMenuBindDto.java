package org.nova.platform.system.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Schema(description = "角色菜单绑定请求")
public class SysRolMenuBindDto {

    @NotBlank
    @Schema(description = "角色id", requiredMode = Schema.RequiredMode.REQUIRED)
    private String rolId;

    @Schema(description = "菜单id集合")
    private List<String> menuIds = new ArrayList<>();
}
