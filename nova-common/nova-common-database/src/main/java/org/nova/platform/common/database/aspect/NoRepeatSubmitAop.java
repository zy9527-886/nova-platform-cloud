package org.nova.platform.common.database.aspect;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.core.ValueOperations;

/**
 * Description:重复提交拦截
 *
 * @author yyg
 * @date 2022/5/16 10:15
 */
@Aspect
@Component
@Slf4j
public class NoRepeatSubmitAop {

//    @Autowired
//    private RedisTemplate redisTemplate;
//
//    /**
//     * 切入点
//     */
//    @Pointcut("@annotation( com.sunline.base.common.util.aspect.NoRepeatSubmit)")
//    public void pt() {
//    }
//
//    @Around("pt()")
//    public Object arround(ProceedingJoinPoint joinPoint) throws Throwable {
//
//        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        HttpServletRequest request = attributes.getRequest();
//        //这里是唯一标识 根据情况而定
//        String key = "1" + "-" + request.getServletPath();
//        // 如果缓存中有这个url视为重复提交
//        if (!redisTemplate.hasKey(key)) {
//            //通过，执行下一步
//            Object o = joinPoint.proceed();
//            //然后存入redis 并且设置15s倒计时
//            ValueOperations operation = redisTemplate.opsForValue();
//            operation.set(key, 0, 15, TimeUnit.SECONDS);
//            //返回结果
//            return o;
//        } else {
//            return ResultUtils.error(ErrorCode.REPEATED_SUBMIT);
//        }
//
//    }

}
