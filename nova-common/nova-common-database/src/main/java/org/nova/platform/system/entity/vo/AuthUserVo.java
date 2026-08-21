package org.nova.platform.system.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.core.keygen.KeyGenerators;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.nova.platform.system.entity.SysRol;
import org.nova.platform.system.entity.SysUser;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Current auth user response.
 *
 * @author yyg
 */
@Getter
@Setter
@Schema(description = "当前登录用户")
public class AuthUserVo  {

    @Schema(description ="用户id")
    @Id(keyType = KeyType.Generator, value = KeyGenerators.snowFlakeId)
    private String userId;

    @Schema(description ="用户名")
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
    private String orgCd;

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

    @Schema(description = "用户角色列表")
    private List<SysRol> roles = new ArrayList<>();
}
