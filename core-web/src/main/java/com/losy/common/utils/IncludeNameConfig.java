package com.losy.common.utils;

import net.sf.json.JsonConfig;
import net.sf.json.util.PropertyFilter;

public class IncludeNameConfig extends JsonConfig{

	public IncludeNameConfig(String namess){
		if(namess != null) {
			final String [] names = namess.split(",");
			super.setJsonPropertyFilter(new PropertyFilter() {
				@Override
				public boolean apply(Object source, String tname, Object value) {
					if(names == null || names.length == 0) return false;
					for (String name : names) {
						if(name.equals(tname)) {
							return false;
						}
					}
					return true;
				}
			});
		}
	}
	
}
