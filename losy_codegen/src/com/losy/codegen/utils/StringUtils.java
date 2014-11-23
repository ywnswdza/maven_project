package com.losy.codegen.utils;

public class StringUtils {

	public static String uncapfirst(String str) {
		if(str == null || "".equals(str)) return "";
		char[] chars=new char[1];  
		chars[0]  = str.charAt(0);
		 String temp=new String(chars);  
		 if(chars[0]>='A'  &&  chars[0]<='Z') {
			 return str.replaceFirst(temp, temp.toLowerCase());
		 } else {
			 return str;
		 }
	}
}
