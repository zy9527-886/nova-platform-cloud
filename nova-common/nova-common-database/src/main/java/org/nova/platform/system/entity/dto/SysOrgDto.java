package org.nova.platform.system.entity.dto;

import org.nova.platform.system.entity.SysOrg;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * 机构信息表(SysOrg)表实体类Dto
 *
 * @author yyg
 * @since 2026-08-13 16:49:18
 */
@Schema(description ="机构信息表Dto")
@Getter
@Setter
public class SysOrgDto extends  SysOrg  {
    @Schema(description = "机构树选中节点代码，用于查询本机构及全部下级")
    private String treeOrgCd;
}

