package org.nova.platform.common.core.exception;


import lombok.Getter;
import lombok.Setter;
import org.nova.platform.common.core.http.StatusCode;

/**
 * Description : 业务异常
 * Author : yyg
 * Version : 1.0.0
 * Since : 1.0.0
 * Date : 2019/8/14
 */
@Setter
@Getter
public class BusinessException extends RuntimeException
{
    /**
     * Description :错误码
     */
    private Integer errorId;

    /**
     * Description :错误信息
     */
    private String errorMsg;

    public BusinessException()
    {
    }

    public BusinessException(StatusCode error)
    {
        super(new StringBuffer(error.getCode()).append(error.getMeg()).toString());
        this.errorId=error.getCode();
        this.errorMsg=error.getMeg();
    }

    public BusinessException(Integer errorId, String errorMsg)
    {
        super(new StringBuffer(errorId).append(errorMsg).toString());
        this.errorId= errorId;
        this.errorMsg=errorMsg;
    }

    public BusinessException( String errorMsg)
    {
        super(new StringBuffer(StatusCode.BUSINESS_ISSUE.getCode()).append(errorMsg).toString());
        this.errorId= StatusCode.BUSINESS_ISSUE.getCode();
        this.errorMsg=errorMsg;
    }

    @Override
    public String toString()
    {
        StringBuffer sb = new StringBuffer(this.getClass().getSimpleName());
        sb.append("执行异常：[Code=").append(this.getErrorId()).append(",Msg=").append(this.getErrorMsg()).append("]");
        return sb.toString();
    }
}
