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
 * 系统配置信息(SysCfg)表实体类
 *
 * @author yyg
 * @since 2026-08-13 16:49:19
 */

@Schema(description ="系统配置信息")
@Getter
@Setter
@Accessors(chain = true)
public class SysCfg  {
    @Schema(description ="id")        
    @Id(keyType = KeyType.Generator, value = KeyGenerators.snowFlakeId) 
    private String id;

    @Schema(description ="配置项代码")      
    private String cfgCd;

    @Schema(description ="配置项名称")      
    private String cfgNm;

    @Schema(description ="配置项值")      
    private String cfgVal;

    @Schema(description ="是否显示")      
    private Integer isDsp;

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

}

