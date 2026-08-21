package org.nova.platform.common.core.http;


import org.nova.platform.common.core.exception.BusinessException;

/**
 * Description: 返参工具类
 *
 * @author 张岳
 * @date 2021/4/13 9:07
 */
public class ResultUtils {
    /**
     * Description:成功
     *
     * @param data 数据
     * @return ResultObj
     * @auther 张岳
     * @date 2021/4/13 9:07
     */
    public static ResultObj suc(Object data) {
        return new ResultObj(true, StatusCode.SUCCESS).setData(data);
    }

    /**
     * Description:成功
     *
     * @param object 数据
     * @param msg 成功提示信息
     * @return ResultObj
     * @auther 张岳
     * @date 2021/4/13 9:07
     */
    public static ResultObj suc(String  msg, Object object) {
        return new ResultObj(true, StatusCode.SUCCESS).setMsg(msg).setData(object);
    }

    /**
     * Description:失败
     *
     * @param code 编码
     * @param msg  消息
     * @return ResultObj
     * @auther 张岳
     * @date 2021/4/13 9:07
     */
    public static ResultObj error(Integer code, String msg) {
        return new ResultObj(false,code,msg);
    }

    public static ResultObj error(Integer code, String msg,String errorMsg) {
        return new ResultObj(false,code,msg,errorMsg,null);
    }

    /**
     * Description:失败
     *
     * @param error 错误枚举
     * @return ResultObj
     * @auther 张岳
     * @date 2021/4/13 9:07
     */
    public static ResultObj error(StatusCode error) {
        return new ResultObj(false,error.getCode(),error.getMeg());
    }

    /**
    * Description:抛异常
    * @auther 张岳
    * @date  2021/4/13 9:07
    * @param msg 提示
    */
    public static  void  throwServiceException(String msg){
        throw new BusinessException(msg);
    }

    /**
     * Description:抛异常
     * @auther 张岳
     * @date  2021/4/13 9:07
     * @param errorId 错误码
     * @param msg 提示
     */
    public  static  void  throwServiceException(Integer errorId, String msg){
        throw new BusinessException(errorId,msg);
    }

    /**
     * Description:抛异常
     * @auther 张岳
     * @date  2021/4/13 9:07
     * @param error 错误类型
     */
    public static  void  throwServiceException(StatusCode error){
        throw new BusinessException(error);
    }
}
