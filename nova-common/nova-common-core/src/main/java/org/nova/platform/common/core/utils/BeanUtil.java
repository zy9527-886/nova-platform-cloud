package org.nova.platform.common.core.utils;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.cglib.core.ReflectUtils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @Author:MQ
 * @Description: bean 转换类
 * @Date:Created in 2018/11/15 0015 11:32
 * @Modified By:
 */
@Slf4j
public class BeanUtil {

	private  BeanUtil(){};

	/**
	 * @Description bean 转map
	 * @param obj
	 * @return Map
	 */
	public static Map<String, Object> transBeanToMap(Object obj) {
		if(obj == null){
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			for (PropertyDescriptor property : propertyDescriptors) {
				String key = property.getName();
				// 过滤class属性
				if (!key.equals("class")) {
					// 得到property对应的getter方法
					Method getter = property.getReadMethod();
					Object value = getter.invoke(obj);

					map.put(key, value);
				}
			}
		} catch (Exception e) {
			log.error("transBeanToMap Error ",e);

		}
		return map;
	}

	/**
	 * @Description bean 转map
	 * @param map map
	 * @param  c  输出的类
	 * @return T
	 */
	public static <T> T transMapToBean(Map<String, Object> map, Class<T> c) {
		T obj = (T) ReflectUtils.newInstance(c);
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			for (PropertyDescriptor property : propertyDescriptors) {
				String key = property.getName();
				if (map.containsKey(key)) {
					Object value = map.get(key);
					// 得到property对应的setter方法
					Method setter = property.getWriteMethod();
					setter.invoke(obj, value);
				}
			}
		} catch (Exception e) {
			log.error("transMapToBean Error ",e);
		}
		return obj;
	}

	/**
	 * @Description	实体 转换 另外实体
	 * @param source 源
	 * @param c 需要转换的entity
	 * @param <T>
	 * @return
	 */
	public static <T> T entityToEntity(Object source, Class<T> c){
		T target = null;
		if(source != null){
			target = (T) ReflectUtils.newInstance(c);
			BeanUtils.copyProperties(source,target);
		}
		return target;
	}

	/**
	 * @Description 取list 第一个
	 * @param sources 源
	 * @param c
	 * @param <T>
	 * @return
	 */
	public static  <T> T listToTopEntity(List sources,Class<T> c){
		T target = null;
		if(sources != null && sources.size() > 0){
			target = (T) ReflectUtils.newInstance(c);
			BeanUtils.copyProperties(sources.get(0),target);
		}
		return target;
	}

	/**
	 *
	 * @param sources
	 * @param c
	 * @param <T>
	 * @return
	 */
	public static  <T> List<T> listToList(List sources,Class<T> c){
		List<T> list=new ArrayList<>();
		if(sources != null && sources.size() > 0){
			for(Object source:sources){
				T target = (T) ReflectUtils.newInstance(c);
				BeanUtils.copyProperties(source,target);
				list.add(target);
			}
		}
		return list;
	}

	public static void copyProperties(Object source, Object target){
		BeanUtils.copyProperties(source,target);
	}

	public static void copyProperties(Object source, Object target,String... copyProperties){
		List<String> copyList = copyProperties != null? Arrays.asList(copyProperties):null;
		Field[] fields = source.getClass().getDeclaredFields();
		List<String> ignorePropertiesList=new ArrayList<>();
		for(Field field:fields){
			if(copyList !=null && !copyList.contains(field.getName())){
				ignorePropertiesList.add(field.getName());
			}
		}
		BeanUtils.copyProperties(source,target,ignorePropertiesList.toArray(new String[ignorePropertiesList.size()]));
	}

	public static Object getObjectFiledValue(Object o,String property){
		Field[] fields = o.getClass().getDeclaredFields();
		List<String> ignorePropertiesList=new ArrayList<>();
		for(Field field:fields){
			if(field.getName().equals(property)){
				try {
					//打开私有访问
					field.setAccessible(true);
					return field.get(o);
				} catch (IllegalAccessException e) {
					log.error("获取属性值报错",e);
				}
			}
		}
		return null;
	}
}
