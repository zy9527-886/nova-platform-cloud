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
 * 数据字典(SysDict)表实体类
 *
 * @author yyg
 * @since 2026-08-13 16:49:19
 */

@Schema(description ="数据字典")
@Getter
@Setter
@Accessors(chain = true)
public class SysDict  {
    @Schema(description ="主键")        
    @Id(keyType = KeyType.Generator, value = KeyGenerators.snowFlakeId) 
    private String id;

    @Schema(description ="字典类型代码（唯一）")      
    private String dictTyp;

    @Schema(description ="字典名称")      
    private String dictNm;

    @Schema(description ="条目备注")      
    private String dictRmk;

    @Schema(description ="父级类型id")      
    private Long prentId;

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

