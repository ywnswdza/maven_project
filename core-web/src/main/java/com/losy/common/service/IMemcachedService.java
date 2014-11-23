package com.losy.common.service;

import net.spy.memcached.MemcachedClient;

public interface IMemcachedService {

	public void doDestroy() throws Exception;
	public MemcachedClient getMemCachedClient();
	public Object get(String key);
	public boolean set(String key,Object obj);
	public boolean delete(String key);
	public boolean set(String key,Object obj,int timeoutSecond);
}
