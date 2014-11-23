package com.losy.common.utils;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Base64;

public class MD5Util {
	/**
	 * SDK用户加密方式
	 * @param s
	 * @return
	 */
	public static String md5Encode(String s) {
		if (s == null) {
			return "";
		}
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			byte[] digest = md5.digest(s.getBytes("utf-8"));
			byte[] encode = Base64.encodeBase64(digest);
			return new String(encode, "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return s;
	}
	
	
	/**
	 * IM , 后台，渠道 及其它系统的加密码方式 
	 * @param plainText
	 * @return
	 */
	public final static String calc(String plainText ) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(plainText.getBytes());
			byte b[] = md.digest();

			int i;

			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if(i<0) i+= 256;
				if(i<16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			return buf.toString().toLowerCase();//32位的加密
		} catch (NoSuchAlgorithmException e) {
		}
		return null;
	}
	
	
	public static final String calc(final String[] ss)throws Exception {
		if (ss == null)
			return "";
		StringBuffer sb = new StringBuffer();
		for (int i = 0,len = ss.length; i < len; i++) {
			if(i>0) sb.append("&");
			sb.append(getCleanString(ss[i]));
		}
		return MD5Util.calc(sb.toString());
	}
	
	
	
	private static Object getCleanString(String string) {
		if(string == null) return "";
		return string.trim();
	}

	public static void main(String[] args) {
		
		
	}

}
