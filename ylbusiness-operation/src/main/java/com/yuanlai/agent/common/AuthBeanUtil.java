package com.yuanlai.agent.common;

import org.springframework.beans.BeanUtils;
import org.springframework.util.ReflectionUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class AuthBeanUtil {

	/**
	 * 简单的bean(Entity)转map
	 * @param bean
	 * @return
	 */
	public static Map<String, Object> toMap(Object bean){
		Map<String, Object> desc = new HashMap<String, Object>();
		PropertyDescriptor[] pds = BeanUtils.getPropertyDescriptors(bean.getClass());
		for(PropertyDescriptor pd : pds){
			if(pd!=null && !"class".equals(pd.getName())){
				Method readMethod = pd.getReadMethod();
				if (readMethod != null) {
					desc.put(pd.getName(),ReflectionUtils.invokeMethod(readMethod, bean));
				}
			}
		}
		return desc;
	}

}
