package org.nova.platform.common.database.aspect;

import java.lang.annotation.*;

/**
 * Description:注解
 *
 * @author yyg
 * @date 2022/5/16 10:18
 */
@Inherited
@Target(ElementType.METHOD) // 作用到方法上
@Retention(RetentionPolicy.RUNTIME) // 运行时有效
public @interface NoRepeatSubmit {

    //名称，如果不给就是要默认的
    String name() default "name";
}
