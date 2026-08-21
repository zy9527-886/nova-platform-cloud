package org.nova.platform.common.core.http;

import lombok.Getter;

/**
 * Copy Right Information : Forms Syntron
 * Project : 四方精创 Java EE 开发平台
 * Description : 错误编码
 * Author : mengqiang
 * Version : 1.0.0
 * Since : 1.0.0
 * Date : 2019/8/14
 */
@Getter
public enum StatusCode {
    SUCCESS(200,"操作成功"),
    SYS_URL_NOT_FOUND(404,"请求未找到"),
    SYS_EXCEPTION(500,"系统异常"),

    BUSINESS_ISSUE(700,"业务异常"),
    NO_PERMISSION(701,"没用权限"),
    LOGOUT(702,"未登录"),
    REPEATED_SUBMIT(703,"重复提交"),
    NO_AUDIT(704,"下一步未设置审核人"),
    USER_NOT_EXIST(705,"用户不存在"),
    USER_EXIST(706,"用户已存在 注册用"),
    USER_LOCKED(707,"用户被锁定"),
    OBJ_EXIST(708,"已存在"),
    MOBILE_EXIST(709,"手机号码已存在"),
    ACCOUNT_LOCKED(710,"账户被冻结"),
    ACCOUNT_RISK(711,"账户被冻结"),
    BANLANCE_NOT_ENOUGH(712,"余额不足"),
    VALID_ISSUE(713,"参数校验异常"),
    PWD_ERROR(714,"密码错误")
    ;

    private  Integer code;
    private  String meg;

    StatusCode(Integer code, String meg) {
        this.code = code;
        this.meg = meg;
    }

}