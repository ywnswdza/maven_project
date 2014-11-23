package com.losy.codegen.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public class ReadPropertiesUtil {

	private static final Properties pro = new Properties();
	private static final Logger log = Logger.getLogger(ReadPropertiesUtil.class);
	
	static {
		InputStream in = null;
		try {
			in = Thread.currentThread().getContextClassLoader().getResourceAsStream("../codeGen.properties");
			log.debug("load classpath sibling path codeGen.properties ........");
			if(in == null) {
				log.debug("load classpath sibling path codeGen.properties not found , load src sibling path codeGen.properties ........");
				try {
					in = new FileInputStream(System.getProperty("user.dir") + File.separator + "codeGen.properties");
					log.debug("load src sibling path codeGen.properties success");
				} catch (Exception e) {
					in = null;
				}
			} else {
				log.debug("load classpath sibling path codeGen.properties success");
			}
			if(in == null) {
				log.debug("load src sibling path codeGen.properties not found , load default codeGen config ......");
				in = Thread.currentThread().getContextClassLoader().getResourceAsStream("codeGen_default.properties");
			}
			pro.load(in);
		} catch (IOException e) {
			log.error("load properties file error !!!!!!");
		} finally {
			try {
				if(in != null)in.close();
			} catch (IOException e) {
				log.error(e);
			}
		}
	}
	
	public static String getValue(String key){
		return getValue(key,"");
	}
	
	public static String getValue(String key,String defaultV){
		String value = "";
		value = pro.getProperty(key, defaultV);
		if("".equals(value) || value == null) value = defaultV;
		return value;
	}
	
	public static Boolean getBooleanValue(String key){
		boolean b = false;
		String value = getValue(key);
		if("".equals(value)) return true;
		try {
			b = Boolean.valueOf(value);
		} catch (Exception e) {
			b = false;
		}
		return b;
	}
}
