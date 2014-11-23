package com.losy.common.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.losy.common.multidatasource.DynamicDataSource;


/**
 * 持有spring BeanFactory 引用，如果没有加载任何properties文件，则负责加载默认的 default.properties
 * 必须保证上类能加载到spring容器中
 * @author Long
 */
public final class SpringContextListener extends PropertyPlaceholderConfigurer implements ApplicationContextAware {

	private static Logger log = Logger.getLogger(SpringContextListener.class);
	private static Properties contextPro;
	private static ApplicationContext beanFactory;
	/**
	 * 此方法加载多个properties 文件
	 */
	@Override
	protected void processProperties(
			ConfigurableListableBeanFactory beanFactoryToProcess,
			Properties props) throws BeansException {
//		log.debug("MyPropertyPlaceholderConfigurer processProperties .......");
		if(props.size() == 0) {
			InputStream in = null;
			try {
				in = Thread.currentThread().getContextClassLoader().getResourceAsStream("config/default.properties");
				if(in != null ) {
					log.info("load default definitions from class path resource [config/default.properties]");
					props.load(in);
					String propss = props.getProperty("include.properties","");
					String [] propertiess = propss.split(",");
					for (String propsPATH : propertiess) {
						if(propsPATH== null) continue;
						if("".equals(propsPATH.trim())) continue;
						in = Thread.currentThread().getContextClassLoader().getResourceAsStream(propsPATH);
						if(in != null) {
							Properties p = new Properties();
							try {
								p.load(in);
								props.putAll(p);
							} catch (Exception e) {
								log.warn("load properties from class path resource [" + propsPATH + "] error",e);
							}
							log.info("load properties from class path resource [" + propsPATH + "]");
						} else {
							log.warn("load properties from class path resource [" + propsPATH + "] error,file not found  ..... ");
						}
					}
				}
			} catch (IOException e) {
				log.warn("load properties from class path resource [default.properties] error,file not found ......");
			} finally {
				try { if(in != null)in.close();} catch (IOException e) {}
			}
		}
		SpringContextListener.contextPro = props;
		log.info("load properties " + props);
		super.processProperties(beanFactoryToProcess, props);
	}

	public static String getContextProValue(String key,String defaultValue) {
		String value = contextPro.getProperty(key, defaultValue);
		if(value == null || "".equals(value)) value = defaultValue;
		return value;
	}

	public static BeanFactory getBeanFactory() {
		return beanFactory;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		if(SpringContextListener.beanFactory != null) {
			throw new RuntimeException("beanFactory inited .............");
		}
		log.info("SpringContextListener init ...........");
		SpringContextListener.beanFactory = applicationContext;
	}

	public static <T> T getBean(String beanName, Class<T> clazs) {  
        return beanFactory.getBean(beanName,clazs);  
	}  
	
	public static <T> T getBean(Class<T> clazz) {
		return beanFactory.getBean(clazz);
	}
	
	@SuppressWarnings("static-access")
	public DynamicDataSource getDBS(){
		return this.getBean("dataSources", DynamicDataSource.class);
	}
}
