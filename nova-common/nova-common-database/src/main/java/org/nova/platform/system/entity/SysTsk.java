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
 * 系统任务(SysTsk)表实体类
 *
 * @author yyg
 * @since 2026-08-13 16:49:17
 */

@Schema(description ="系统任务")
@Getter
@Setter
@Accessors(chain = true)
public class SysTsk  {
    @Schema(description ="id")        
    @Id(keyType = KeyType.Generator, value = KeyGenerators.snowFlakeId) 
    private String tskId;

    @Schema(description ="任务类型")      
    private String tskTyp;

    @Schema(description ="任务名")      
    private String tskNm;

    @Schema(description ="任务表名")      
    private String tskTbl;

    @Schema(description ="任务日期")      
    private String tskDt;

    @Schema(description ="状态")      
    private String stus;

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

