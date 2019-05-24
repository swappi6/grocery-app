package org.grocery.mapper;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class Mapper {
	
	public void copyProperties(Object destination, Object origin) {
		try {
			BeanUtils.copyProperties(destination, origin);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
