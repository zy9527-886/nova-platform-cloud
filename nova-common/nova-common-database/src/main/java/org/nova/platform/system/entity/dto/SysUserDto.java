package org.nova.platform.system.entity.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.core.keygen.KeyGenerators;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.nova.platform.system.entity.SysUser;
import io.swagger.v3.oas.annotations.media.Schema;
import org.nova.platform.system.entity.SysUserRol;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * 银行用户表(SysUser)表实体类Dto
 *
 * @author yyg
 * @since 2026-08-13 14:27:34
 */
@Getter
@Setter
@Schema(description ="银行用户表Dto")
public class SysUserDto   {
    @Schema(description ="用户id")
    private String userId;

    @Schema(description ="用户名")
    private String userNm;

    @Schema(description ="备注")
    private String rmk;

    @Schema(description ="用户密码")
    private String pwd;

    @Schema(description ="证件类型")
    private String idTyp;

    @Schema(description ="证件号")
    private String idNo;

    @Schema(description ="真实姓名")
    private String realNm;

    @Schema(description ="联系电话")
    private String tel;

    @Schema(description ="所属机构编码")
    private String orgCd;

    @Schema(description ="用户状态")
    private String stus;

    @Schema(description ="头像图标")
    private String icon;

    List<SysUserRol> userRolList;
}

