package org.nova.platform.common.core.aspect;


import com.alibaba.fastjson2.JSONObject;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.nova.platform.common.core.exception.BusinessException;
import org.nova.platform.common.core.utils.ValidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Enumeration;

import static com.alibaba.fastjson2.JSONWriter.Feature.WriteNulls;

/**
 * Description: 请求参数log
 *
 * @author yyg
 * @date 2021/4/10 11:23
 */
@Component
@Aspect
@Slf4j
@ConditionalOnProperty(value = "base.log.enabled", havingValue = "true")
public class LogAspect {

    @Value("${base.log.result.enabled}")
    private boolean resultEnabled;

    @Autowired
    private HttpServletRequest request;

    /**
     * Description:打印日志
     *
     * @param pjp 切点
     * @return Object
     * @auther yyg
     * @date 2021/4/10 11:23
     */
    @Around("execution(public * org.nova.platform.*..controller..*.*(..))")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        String url = request.getRequestURL().toString();
        String ip = request.getRemoteAddr();
        String method = request.getMethod();
        Object[] params = pjp.getArgs();
        Object param = null;
        //String uri = request.getRequestURI();
        log.info("请求开始, 各个参数, \n url ==> {},\n ip==>{},\n method ==> {},\n header ==> {},\n body ==> {}",
                url, ip, method, changeEnumerationHeader(request), JSONObject.toJSONString(params, WriteNulls));

        //参数校验
        if (params.length > 0) {
            param = params[0];
            //参数校验
            String checkRes = ValidUtil.cheakEntity(param);
            if (StringUtils.isNoneBlank(checkRes)) {
                throw new BusinessException(checkRes);
            }
        }

        // result的值就是被拦截方法的返回值
        Object result = pjp.proceed();
        if (resultEnabled) {
            log.info("请求结束，controller的返回值是 " + JSONObject.toJSONString(result, WriteNulls));
        }
        return result;
    }

    private String changeEnumerationHeader(HttpServletRequest request) {
        Enumeration er = request.getHeaderNames();
        StringBuffer sb = new StringBuffer();
        while (er.hasMoreElements()) {
            String paraName = (String) er.nextElement();
            sb.append(paraName);
            sb.append("=");
            sb.append(request.getHeader(paraName));
            sb.append(";");
        }
        return sb.toString();
    }

    private String getBodyString(HttpServletRequest request) {
        String body = null;
        ServletInputStream inputStream = null;
        Object var5;
        try {
            inputStream = request.getInputStream();
            body = IOUtils.toString(inputStream, "UTF-8");
            return body;
        } catch (IOException var9) {
            var5 = null;
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
        return (String) var5;
    }

}
