package com.losy.common.utils;

import java.security.MessageDigest;

public class EncryptUtil {
	
	/**
	 * 使用md5加密
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static byte[] MD5(byte[] data) throws Exception{
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		md5.update(data);
		return md5.digest();
	}
	
	public static StringBuffer MD5OfHexString(byte[] data)throws Exception{
		StringBuffer buf = new StringBuffer();
		byte[] b = MD5(data);
		for(int i=0;i<b.length;i++){
			buf.append(Integer.toHexString(b[i]));
		}
		return buf;
	}
}
