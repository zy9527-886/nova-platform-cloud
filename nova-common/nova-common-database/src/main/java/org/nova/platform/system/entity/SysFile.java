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
 * 文件信息(SysFile)表实体类
 *
 * @author yyg
 * @since 2026-08-13 16:49:18
 */

@Schema(description ="文件信息")
@Getter
@Setter
@Accessors(chain = true)
public class SysFile  {
    @Schema(description ="id")        
    @Id(keyType = KeyType.Generator, value = KeyGenerators.snowFlakeId) 
    private String id;

    @Schema(description ="文件名称")      
    private String fileNm;

    @Schema(description ="文件大小")      
    private Integer fileSize;

    @Schema(description ="文件类型")      
    private String fileTyp;

    @Schema(description ="文件路径")      
    private String filePath;

    @Schema(description ="影像平台编号")      
    private String tripleNo;

    @Schema(description ="桶名")      
    private String bucket;

    @Schema(description ="对象名")      
    private String objNm;

    @Schema(description ="存储类型$$$1:MinIO,2:NAS")      
    private String strgTyp;

    @Schema(description ="是否关联业务")      
    private Integer isBiz;

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

