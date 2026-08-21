package org.nova.platform.system.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.RelationOneToMany;
import com.mybatisflex.core.keygen.KeyGenerators;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import org.nova.platform.system.entity.SysRol;
import org.nova.platform.system.entity.SysUser;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * 银行用户表(SysUser)表实体类Vo
 *
 * @author yyg
 * @since 2026-08-13 14:27:19
 */
@Schema(description ="银行用户表Vo")
public class SysUserVo extends  SysUser  {
    @Schema(description ="用户id")
    @Id(keyType = KeyType.Generator, value = KeyGenerators.snowFlakeId)
    private String userId;

    @Schema(description ="用户名")
    @NotBlank
    private String userNm;

    @Schema(description ="备注")
    private String rmk;

    @Schema(description ="证件类型")
    private String idTyp;

    @Schema(description ="证件号")
    private String idNo;

    @Schema(description ="真实姓名")
    private String realNm;

    @Schema(description ="联系电话")
    private String tel;

    @Schema(description ="所属机构编码")
    private String orgId;

    @Schema(description ="用户状态")
    private String stus;

    @Schema(description ="密码错误次数")
    private Integer pwdErrTms;

    @Schema(description ="密码修改时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date pwdChgTm;

    @Schema(description ="最近登录时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lstLgnTm;

    @Schema(description ="锁定时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lockTm;

    @Schema(description ="头像图标")
    private String icon;

    @Schema(description ="创建人")
    private String crePer;

    @Schema(description ="创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date creTm;

    @Schema(description ="更新人")
    private String updtPer;

    @Schema(description ="更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updtTm;

    List<SysRol> roles;
}

