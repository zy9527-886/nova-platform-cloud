package org.nova.platform.system.entity;

import java.util.Date;
import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.core.keygen.KeyGenerators;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import io.swagger.v3.oas.annotations.media.Schema;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 机构信息表(SysOrg)表实体类
 *
 * @author yyg
 * @since 2026-08-13 16:49:18
 */

@Schema(description ="机构信息表")
@Getter
@Setter
@Accessors(chain = true)
public class SysOrg  {
    @Schema(description ="机构编号")        
    @Id(keyType = KeyType.Generator, value = KeyGenerators.snowFlakeId) 
    private String orgId;

    @Schema(description ="机构代码,后台自动生成，四位数字代表一个层级，每个层级0001开始，层级内容依次累加，同一层级数值累加，例：000100010001三级网点，以便于根据上级机构查询下级")      
    private String orgCd;

    @Schema(description ="机构名称")      
    private String orgNm;

    @Schema(description ="上级机构编码")      
    private String orgPrentId;

    @Schema(description ="机构级别代码")      
    private String orgLvCd;

    @Schema(description ="机构地址")      
    private String orgAddr;

    @Schema(description ="联系电话;固定电话格式")      
    private String telNo;

    @Schema(description ="联系人")      
    private String ctctPer;

    @Schema(description ="机构状态")      
    private String orgStus;

    @Schema(description ="机构描述")      
    private String orgDesc;

    @Schema(description ="机构标志")      
    private Integer orgFlg;

    @Schema(description ="法人编码")      
    private String corpId;

    @Schema(description ="清算机构编号;对应行内清算机构")      
    private String clrgOrgId;

    @Schema(description ="安心签机构号")      
    private String safeSignOrgId;

    @Schema(description ="备注字段")      
    private String rmk;

    @Schema(description ="创建人")      
    private String crePer;

    @Schema(description ="创建时间")      
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(onInsertValue = "now()")
    private Date creTm;

    @Schema(description ="更新人")      
    private String updtPer;

    @Schema(description ="更新时间")      
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(onUpdateValue = "now()", onInsertValue = "now()")
    private Date updtTm;

    @Schema(description ="清算行号")      
    private String clrgLineCd;

    @Schema(description ="手续费账户机构")      
    private String feeOrgId;

    @Schema(description ="资金账户机构")      
    private String fundOrgId;

    @Schema(description ="扩展信息")      
    private String extras;

}

