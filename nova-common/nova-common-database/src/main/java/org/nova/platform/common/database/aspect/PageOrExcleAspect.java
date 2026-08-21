package org.nova.platform.common.database.aspect;


import com.mybatisflex.core.paginate.Page;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.nova.platform.common.core.http.ResultUtils;
import org.nova.platform.common.core.utils.DateUtil;
import org.nova.platform.common.core.utils.excel.ExportExcel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.util.Date;
import java.util.List;

/**
 * Description: 分页，导出，机构权限拦截器
 *
 * @author yyg
 * @date 2021/4/10 16:42
 */
@Aspect
@Component
public class PageOrExcleAspect {
    /**
     * response
     */
    @Autowired
    private HttpServletResponse response;

    @Autowired
    private HttpServletRequest request;

    /**
     * Description 分页导excle拦截器
     *
     * @param joinPoint   切点
     * @param pageOrExcel 注释类
     * @return Object
     * @auther yyg
     * @date 2021/4/10 16:42
     */
    @Around("execution(* org.nova.platform.*..service..*(..)) && @annotation(pageOrExcel)")
    public Object pageOrExcleAround(ProceedingJoinPoint joinPoint, PageOrExcel pageOrExcel) throws Throwable {
        //todo 机构权限塞入
        Object[] args=joinPoint.getArgs();
        if(args.length>0 && args[0] instanceof Page ){//导出excel
            String name=pageOrExcel.title()+"_"+ DateUtil.DateTOF2(new Date())+".xlsx";
            List record=  ((Page)joinPoint.proceed()).getRecords();
            if(record.size()>0){
                new ExportExcel(name,record.get(0).getClass())
                        .setDataList(record)
                        .write(response,name).dispose();
                return  null;
            }else {
                ResultUtils.throwServiceException("excel数据为空");
            }
        }
        return joinPoint.proceed();
    }
}