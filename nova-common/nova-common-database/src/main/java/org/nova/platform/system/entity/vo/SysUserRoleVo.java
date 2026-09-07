package org.nova.platform.system.entity.vo;

import lombok.Getter;
import lombok.Setter;
import org.nova.platform.system.entity.SysRol;

/** A role row enriched with its owning user id for batch assembly. */
@Getter
@Setter
public class SysUserRoleVo extends SysRol {
    private String userId;
}
