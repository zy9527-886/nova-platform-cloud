package org.nova.platform.system.entity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Schema(description = "机构树节点")
@Getter
@Setter
@Accessors(chain = true)
public class SysOrgTreeVo {
    private String orgId;
    private String orgCd;
    private String orgNm;
    private String orgPrentId;
    private String orgStus;
    private List<SysOrgTreeVo> children = new ArrayList<>();
}
