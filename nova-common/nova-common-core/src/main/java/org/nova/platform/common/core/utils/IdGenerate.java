package org.nova.platform.common.core.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @Author:MQ
 * @Description: 生成唯一ID
 * @Date:Created in 2018/11/15 0015 11:32
 * @Modified By:
 */
public class IdGenerate {
	private static long tmpID = 0;
	private static boolean locked = false;

	/**
	 * 封装JDK自带的UUID, 通过Random数字生成, 中间无-分割.
	 */
	public static String getUUID() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	/**
	 * Description: 18位id
	 * @auther 张岳
	 * @date 2021/11/15
	 * @return String
	 */
	public static String  get18UUID(){
		// 1.开头两位，标识业务代码或机器代码（可变参数）
		Integer machineId = 01;
		// 2.中间四位整数，标识日期
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
		String dayTime = sdf.format(new Date());
		// 3.生成uuid的hashCode值
		int hashCode = UUID.randomUUID().toString().hashCode();
		// 4.可能为负数
		if(hashCode < 0){
			hashCode = -hashCode;
		}
		// 5.算法处理: 0-代表前面补充0; 10-代表长度为10; d-代表参数为正数型
		String value = machineId + dayTime + String.format("%010d", hashCode);
		return value;
	}

	/**
	 * Description: 16位id
	 * @auther 张岳
	 * @date 2021/11/15
	 * @return String
	 */
	public static String  get16UUID(){
		// 2.中间四位整数，标识日期
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
		String dayTime = sdf.format(new Date());
		// 3.生成uuid的hashCode值
		int hashCode = UUID.randomUUID().toString().hashCode();
		// 4.可能为负数
		if(hashCode < 0){
			hashCode = -hashCode;
		}
		// 5.算法处理: 0-代表前面补充0; 10-代表长度为10; d-代表参数为正数型
		String value = dayTime + String.format("%010d", hashCode);
		return value;
	}

}
