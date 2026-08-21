package org.nova.platform.common.core.aspect;


import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.nova.platform.common.core.exception.BusinessException;
import org.nova.platform.common.core.http.ResultObj;
import org.nova.platform.common.core.http.ResultUtils;
import org.nova.platform.common.core.http.StatusCode;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;


import java.util.List;

/**
 * Description:全局处理
 *
 * @author yyg
 * @date 2021/4/13 9:00
 */
@RestControllerAdvice
@Slf4j
public class GlobalHandler   {


    /**
     * Description: 全局参数校验
     * @param res 返回
     * @param e 错误
     * @return ResultObj
     */
    @ResponseBody
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResultObj handleValidException(HttpServletResponse res, MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        StringBuilder message = new StringBuilder();
        if (bindingResult.hasErrors()) {
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            for (ObjectError allError : allErrors) {
                message.append(allError.getObjectName()+":"+allError.getDefaultMessage()).append(";");
            }
        }
        message.deleteCharAt(message.length()-1);
        res.setStatus(StatusCode.VALID_ISSUE.getCode());
        String errorMsg= message.toString();
        return ResultUtils.error(StatusCode.VALID_ISSUE.getCode(),errorMsg,"input params error: "+errorMsg);
    }

    /**
     * Description: 全局业务异常
     * @auther yyg
     */
    @ExceptionHandler(BusinessException.class)
    public ResultObj handStatusCodeException(HttpServletResponse res, BusinessException e) {
        log.error("****** BusinessException: ",e);
        res.setStatus(e.getErrorId());
        return ResultUtils.error(e.getErrorId(),e.getErrorMsg(),e.toString());
    }
    /**
     * Description: 全局系统异常
     * @auther yyg
     */
   @ExceptionHandler(Exception.class)
    public ResultObj allException(HttpServletResponse res, Exception e) {
      log.error("****** Exception: ",e);
      res.setStatus(500);
      return ResultUtils.error(StatusCode.SYS_EXCEPTION).setErrorMsg(e.toString());
    }

  /*  @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        //支持返回所有类型
        return true;
    }*/
    /*@Override
    @Nullable
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        // o is instanceof ConmmonResponse -> return o
        if (o instanceof ResultObj) {
            return  o;
        }
        // string 特殊处理
        if (o instanceof String) {
            return JSON.toJSON(ResultUtils.suc(o)).toString();
        }
         if(o instanceof LinkedHashMap  ){

                Integer status = (Integer) ((LinkedHashMap) o).get("status");
                switch (status){
                    case 401:
                       return ResultUtils.error(ErrorCode.LOGOUT)  ;
                    case 404:
                      return   ResultUtils.error(ErrorCode.SYS_URL_NOT_FOUND) ;
                    default:
                        break;

                }
            }

        return  ResultUtils.suc(o);
    }*/
}