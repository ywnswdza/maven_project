package com.losy.common.service.impl;

import java.util.Properties;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.losy.common.service.IMemcachedService;
import com.losy.common.utils.SpringContextListener;

import net.spy.memcached.AddrUtil;
import net.spy.memcached.MemcachedClient;

@Service("memcachedService")
public class MemcachedServiceImpl implements IMemcachedService{
	
	private static final Logger logger = Logger.getLogger(MemcachedServiceImpl.class);
	private MemcachedClient mcc = null;
	static{
		Properties systemProperties = System.getProperties();
		systemProperties.put("net.spy.log.LoggerImpl", "net.spy.memcached.compat.log.Log4JLogger");
		System.setProperties(systemProperties);
	}

	public MemcachedServiceImpl() {
		try {
			if(!"false".equals(SpringContextListener.getContextProValue("use","false"))){
		        String serverlist = SpringContextListener.getContextProValue("serverlist", "");
		        serverlist = serverlist.replaceAll(",", " ");
		        mcc = new MemcachedClient(AddrUtil.getAddresses(serverlist));
		        logger.info(serverlist);
		        logger.info("use memcached!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
			}else{
				logger.info("not use memcached!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
	}

	public void doDestroy() throws Exception {
		if(mcc != null)
		mcc.shutdown();
	}
	
	public MemcachedClient getMemCachedClient(){
		return mcc;
	}
	
	public Object get(String key){
		Object obj = null;
		try{
			if(mcc != null)
				obj = mcc.get(key);
			else
				logger.info("mc is null "+key);
		}catch(Exception e){
			logger.error(e.getMessage());
		}
		return obj;
	}

	public boolean set(String key,Object obj){
		return set(key,obj,0);
	}
	
	public boolean delete(String key){
		if(mcc != null)
			mcc.delete(key);
		else
			logger.info("mc is null "+key);
		return true;
	}
	
	public boolean set(String key,Object obj,int timeoutSecond){
		if (key == null || key.length() == 0) {
			logger.error("key==null || key.length()==0");
			return false;
		}
		if (key.length() >= 250) {
            logger.error("key.length() >= 250");
            return false;
        }
		if(mcc != null)
			mcc.set(key,timeoutSecond,obj);
		else
			logger.info("mc is null "+key);
		return true;
	}
}
