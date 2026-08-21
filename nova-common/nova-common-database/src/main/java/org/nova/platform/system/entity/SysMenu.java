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
 * 银行用户菜单权限(SysMenu)表实体类
 *
 * @author yyg
 * @since 2026-08-13 16:49:17
 */

@Schema(description ="银行用户菜单权限")
@Getter
@Setter
@Accessors(chain = true)
public class SysMenu  {
    @Schema(description ="菜单ID")        
    @Id(keyType = KeyType.Generator, value = KeyGenerators.snowFlakeId) 
    private String menuId;

    @Schema(description ="菜单名称")      
    private String menuNm;

    @Schema(description ="权限码")      
    private String permCd;

    @Schema(description ="路径")      
    private String path;

    @Schema(description ="父菜单ID")      
    private String prentId;

    @Schema(description ="排序")      
    private Integer sort;

    @Schema(description ="图标")      
    private String icon;

    @Schema(description ="类型;按钮:0、菜单:1")
    private String typ;

    @Schema(description ="是否显示")      
    private Integer isDsp;

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

    @Schema(description ="菜单来源")      
    private String menuSource;

}

