package com.losy.common.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class HttpUtils {

	public static String get(String url) {
		URL urll = null;
		HttpURLConnection conn = null;
		try {
			urll = new URL(url);
			conn = (HttpURLConnection) urll.openConnection();
			conn.setRequestMethod("GET");
			conn.setReadTimeout(3000);
			conn.connect();
			if (conn.getResponseCode() == 200) {
				return readIn(conn.getInputStream());
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(conn != null) conn.disconnect();
		}
		return "";
	}
	
	public static String post(String url) {return post(url,null);}
	public static String post(String url,Map<String,String> params) {
		URL urll = null;
		HttpURLConnection conn = null;
		try {
			urll = new URL(url);
			conn = (HttpURLConnection) urll.openConnection();
			conn.setRequestMethod("POST");
			conn.setDoInput(true);
			conn.setDoOutput(true);
			if(params != null && params.size() > 0) {
				OutputStream os = conn.getOutputStream();
				DataOutputStream  out = new DataOutputStream (os);
				for(String key : params.keySet()) {
					out.writeBytes(key + "=" +  URLEncoder.encode(params.get(key),"UTF-8")  + "&");
				}
				out.flush();
			    out.close(); 
			}
			conn.setReadTimeout(3000);
			conn.connect();
			if (conn.getResponseCode() == 200) {
				return readIn(conn.getInputStream());
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(conn != null) conn.disconnect();
		}	
		return "";
	}
	
	
	private static String readIn(InputStream in) throws IOException {
		StringBuffer sb = new StringBuffer();
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String line = null;
		while((line = br.readLine()) != null) {
			sb.append(line).append("\n");
		}
		if(sb.indexOf("\n") > 0) sb.setLength(sb.length() -  "\n".length());
		return sb.toString();
	}
	
	
	public static void main(String[] args) {
		Map<String,String> map = new HashMap<String, String>();
		map.put("userAccount", "比");
		map.put("password", "123456");
		System.out.println(get("http://localhost:8080/losy/sessionValid/validation.do"));
	}
}
