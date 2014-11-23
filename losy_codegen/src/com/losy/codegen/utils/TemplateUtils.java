package com.losy.codegen.utils;

import java.io.IOException;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.log4j.Logger;

import freemarker.cache.ClassTemplateLoader;
import freemarker.cache.MultiTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;


public class TemplateUtils {
	private TemplateUtils(){}
	private static Configuration cfg = null;
	private static final Logger log = Logger.getLogger(TemplateUtils.class);
	private static ReadWriteLock wrl = new ReentrantReadWriteLock();
	public static Template getTemplate(String templatename,String charset){
		try {
			if(cfg == null) throw new RuntimeException("Configuration not init,pleace use initTempConfig method .....");
			return cfg.getTemplate(templatename,charset);
		} catch (IOException e) {
			log.error(e.getMessage(),e);
			return null;
		}
	}
	public static Template getTemplate(String templatename){
		return getTemplate(templatename, "UTF-8");
	}
	
	public static void initTempConfig(String rootPath){
		wrl.readLock().lock();
		try {
			if(cfg == null) {
				TemplateLoader multiload = null;
				try {
					wrl.readLock().unlock();
					wrl.writeLock().lock();
					if(cfg != null) return;
					//TemplateLoader load1 = new ClassTemplateLoader(TemplateUtils.class,rootPath);
					ClassTemplateLoader ctl = new ClassTemplateLoader(TemplateUtils.class,rootPath);
				//	JarTemplateLoad jtl = new JarTemplateLoad(rootPath);
					multiload = new MultiTemplateLoader(new TemplateLoader[]{ctl});
					cfg = new Configuration();
					cfg.setTemplateLoader(multiload);
				} catch (Exception e) {
					throw e;
				} finally {
					wrl.readLock().lock();
					wrl.writeLock().unlock();
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		} finally {
			wrl.readLock().unlock();
		}
	}
}
