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
 * 日历信息(SysCald)表实体类
 *
 * @author yyg
 * @since 2026-08-13 16:49:19
 */

@Schema(description ="日历信息")
@Getter
@Setter
@Accessors(chain = true)
public class SysCald  {
    @Schema(description ="主键")        
    @Id(keyType = KeyType.Generator, value = KeyGenerators.snowFlakeId) 
    private String id;

    @Schema(description ="日期")      
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date caldDt;

    @Schema(description ="是否工作日")      
    private Integer isWork;

    @Schema(description ="备注")      
    private String rmk;

    @Schema(description ="创建人")      
    private String crePer;

    @Schema(description ="创建时间")      
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(onInsertValue = "now()")
    private Date creTm;

}

