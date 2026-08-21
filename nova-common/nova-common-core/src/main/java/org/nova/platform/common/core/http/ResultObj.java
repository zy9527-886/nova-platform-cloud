package org.nova.platform.common.core.http;



import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 结果封装
 */
@Getter
@Setter
@Accessors(chain = true)
@Schema(name = "ResultObj", description = "通用对象")
public class ResultObj implements Serializable {
	private static final long serialVersionUID = 955268998822198731L;
	/**
	 * Description:是否成功
	 */
	@Schema(name = "suc", description = "是否成功")
	private Boolean suc;

	/**
	 * Description:编码
	 */
	@Schema(name = "code", description = "状态码")
	private Integer code;

	/**
	 * Description:提示信息
	 */
	@Schema(name = "msg", description = "提示信息")
	private String msg;

	/**
	 * Description:失败原因
	 */
	@Schema(name = "errorMsg", description = "异常信息")
	private String errorMsg;

	/**
	 * Description:数据
	 */
	@Schema(name = "data", description = "数据实体")
	private Object data;

	public ResultObj(){
	}

	public ResultObj(Boolean suc, Integer code, Object data) {
		this.suc = suc;
		this.code = code;
		this.data = data;
	}

	public ResultObj(Boolean suc, Integer code, String msg) {
		this.suc = suc;
		this.code = code;
		this.msg = msg;
	}

	public ResultObj(Boolean suc, Integer code, String msg, Object data) {
		this.suc = suc;
		this.code = code;
		this.msg = msg;
		this.data = data;
	}

	public ResultObj(Boolean suc, Integer code, String msg, String errorMsg, Object data) {
		this.suc = suc;
		this.code = code;
		this.msg = msg;
		this.errorMsg = errorMsg;
		this.data = data;
	}

	public ResultObj(Boolean suc, StatusCode err) {
		this.suc = suc;
		this.code = err.getCode();
		this.msg = err.getMeg();
	}
}
