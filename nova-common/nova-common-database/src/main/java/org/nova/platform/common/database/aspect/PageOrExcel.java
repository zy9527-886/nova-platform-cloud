package org.nova.platform.common.database.aspect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Description: 分页,导出,机构权限buff
 *
 * @author yyg
 * @date 2021/4/10 16:23
 */
@Inherited
@Target({ElementType.PARAMETER,ElementType.METHOD})
@Retention(RUNTIME)
public @interface PageOrExcel {

    /**
     * Description:导出的标题
     * @return String
     */
    String title() default "导出";

    /**
     * Description:导出的类型
     * @return String
     */
    String dataType() default "java.util.Map";

    /**
    * Description:机构权限
    * @return String
    */
    String orgPower() default "-1";
}
