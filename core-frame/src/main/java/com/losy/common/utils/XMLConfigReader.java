package com.losy.common.utils;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

/**
 * dom4j xml读取
 * @author Administrator
 *
 */
public class XMLConfigReader {
	/**
	 * 防止重复加载
	 */
	public static final Map<String,Document> maps = new HashMap<String, Document>();
	private static final Logger log = Logger.getLogger(XMLConfigReader.class);
	private static XMLConfigReader xcf = null; 
	
	private XMLConfigReader(){
		this.initConfig();
	}
	
	public static XMLConfigReader getInstance(){
		synchronized (log) {
			if(xcf == null) xcf = new XMLConfigReader();
			return xcf;
		}
	}
	 
	/**
	 *  
	 * @param classpath下的文件名全路径
	 * @param xpath 
	 * @return
	 */
	public String getValue(String xpath){
		Node node = findNodeByPath(xpath);
		if(node == null) return "";
		return node.getText();
	}
	
	
	private void initConfig(){
		String fileNames = SpringContextListener.getContextProValue("include.xmlconfig", "");
		for (String fileName : fileNames.split(",")) {
			try {
				Document doc = new SAXReader().read(Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName));
				maps.put(fileName,doc);
			} catch (DocumentException e) {
				log.error("read [" + fileName + "] xml config error !!!");
			}
		}
	}
	
	private Node findNodeByPath(String xpath) {
		Node node = null;
		for (String key : maps.keySet()) {
			Document doc = maps.get(key);
			node = doc.selectSingleNode(xpath);
			if(node != null) break;
		}
		return node;
	}
}
