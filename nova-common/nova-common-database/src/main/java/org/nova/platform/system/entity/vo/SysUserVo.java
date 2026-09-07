package org.nova.platform.system.entity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.nova.platform.system.entity.SysRol;
import org.nova.platform.system.entity.SysUser;

import java.util.ArrayList;
import java.util.List;

/**
 * 银行用户及其角色视图。
 */
@Getter
@Setter
@Schema(description = "银行用户表Vo")
public class SysUserVo extends SysUser {

    @Schema(description = "用户角色")
    private List<SysRol> roles = new ArrayList<>();
}
