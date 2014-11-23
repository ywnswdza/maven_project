/*package com.losy.common.aop;

import java.io.Serializable;

import javax.annotation.Resource;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
*//**
 * 缓存拦截器,未启用。未在配置中扫描此包,未实现功能，只是模板
 * @author longyt
 *
 *//*
@Aspect
public class CacheAspectInterceptor {

	private static final Logger log = Logger.getLogger(CacheAspectInterceptor.class);
	
	public CacheAspectInterceptor() {
log.info("CacheAspectInterceptor init .......");	
	}
	
	private Cache cache;
		
	@Resource(name="simpleCache")
	public void setCache(Cache cache) {
		this.cache = cache;
log.info("Cache [" + cache.getName() + "] in CacheAspectInterceptor inited ......");		
	}
	
	*//** 
	 * 声明登入切入点  <br/>
	 * execution(* com.newer.service..*.*(..) <br/>
	 * execution(1 com.newer.service..2.3(..) <br/>
	 * 1. 表示返回值类型，例如：java.lang.String，
	 * 	  * 号代表任何返回类型 ，!void表示任意返回值类型(必须有返回值)<br/>
	 * 2. 表示类，* 号代表对所有类拦截 <br/>
	 * 3. 表示方法，* 号代表所有方法 <br/>
	 * service.. 这两个点代表可以对service下的子包进行拦截 <br/>
	 * (..) 表示方法的参数可以可无，一个也行，多个亦可，例如：(java.lang.String,..)
	 * @throws Throwable 
	 *//*
	
	@Pointcut("execution(* com.xxwan.*.service..*.get*(..)) ")
	public void cacheQueryPointcut(){*//**缓存拦截器查询时切入点*//*}
	
	@Pointcut("execution(* com.xxwan.*.service..*.save*(..)) ")
	public void cacheUpdatePointcut(){*//**缓存拦截器更新时切入点*//*}
	
	@Around("cacheQueryPointcut() && args(id,..)")
	public Object cacheQueryAround(ProceedingJoinPoint jp,Serializable id){
		Object obj = null;
		String methodName = jp.getSignature().getName(); //方法名
		Object target = jp.getTarget(); //拦截的目标类
		//拦截的方法参数
		Object[] args = jp.getArgs();
		String cacheKey = jp.getTarget().getClass().getName() + "_" + methodName + "_" + id;
log.debug("cacheKey : " + cacheKey );	
		try {
			Element element = cache.get(cacheKey);
			if(element == null) {
log.debug("[" + cache.getName() + "] not exists key [ " + cacheKey + "],query database ......");				
				obj = jp.proceed(args);
				if(obj != null) {
					element = new Element(cacheKey, obj);
					cache.put(element);
				} else {
					log.debug(cacheKey + " not in database ......");
				}
			} else {
				obj = element.getValue();
log.debug("[" + cache.getName() + "] has key [ " + cacheKey + "], query from cache ......");				
			}
		} catch (Throwable e) {
log.error("CacheAspectInterceptor invock ["+target.getClass().getName() + methodName + " error .......",e);
			e.printStackTrace();
		}
		log.debug("==============cache use count：" + cache.getMemoryStoreSize());	
		return obj;
	}
	
	@Around("cacheUpdatePointcut()")
	public Object cacheUpdateAround(ProceedingJoinPoint jp){
		Object obj = null;
log.debug("==============cache use count ：" + cache.getMemoryStoreSize());	
		//生成cacheKey
		//根据cacheKey从cache判断是否存在内存或硬盘中，存在则移除
		//调用目标的更新或新增方法或删除方法
		//将列新后的结果更新到缓存中
		return obj;
	}
	
	private void beforeTransationHandle(JoinPoint point) throws Exception{
		//拦截的实体类
		Object target = point.getTarget();
		//拦截的方法名称
		String methodName = point.getSignature().getName();
		//拦截的方法参数
		Object[] args = point.getArgs();
		//拦截的放参数类型
		Class[] parameterTypes = ((MethodSignature)point.getSignature().getMethod().getParameterTypes();
		Method m = null;
		try {
			//通过反射获得拦截的method
			m = target.getClass().getMethod(methodName, parameterTypes);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
	}
	
}
*/