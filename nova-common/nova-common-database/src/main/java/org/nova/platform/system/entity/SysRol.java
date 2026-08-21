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
 * 银行用户角色表(SysRol)表实体类
 *
 * @author yyg
 * @since 2026-08-13 16:49:17
 */

@Schema(description ="银行用户角色表")
@Getter
@Setter
@Accessors(chain = true)
public class SysRol  {
    @Schema(description ="角色id")        
    @Id(keyType = KeyType.Generator, value = KeyGenerators.snowFlakeId) 
    private String rolId;

    @Schema(description ="角色名")      
    private String rolNm;

    @Schema(description ="角色代码;定义后用作特殊识别使用")      
    private String rolCd;

    @Schema(description ="角色级别")      
    private String rolLv;

    @Schema(description ="角色描述")      
    private String rolDesc;

    @Schema(description ="机构编号;为ALL时为通用角色，不可被修改")      
    private String orgId;

    @Schema(description ="是否公共")      
    private Integer isPub;

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

}

