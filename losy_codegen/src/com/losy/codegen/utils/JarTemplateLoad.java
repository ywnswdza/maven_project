package com.losy.codegen.utils;

import java.io.IOException;
import java.io.Reader;
import java.net.URL;
import java.util.Enumeration;
import java.util.Locale;

import org.apache.log4j.Logger;

import freemarker.cache.TemplateLoader;

public class JarTemplateLoad implements TemplateLoader{

	private String prefix;
	public static final Logger log = Logger.getLogger(JarTemplateLoad.class);
	
	public JarTemplateLoad(String prefix) {
		
		this.prefix = prefix;
	}

	@Override
	public Object findTemplateSource(String name) throws IOException {
		Locale locale = Locale.getDefault();
		String entryPath = prefix + "\\" + name.replace("_" + locale.toString(), "");
		entryPath = entryPath.replaceAll("\\\\", "/");
		Enumeration<URL> re = Thread.currentThread().getContextClassLoader().getResources(entryPath);
		while(re.hasMoreElements()) {
			URL u = re.nextElement();
			log.info(u.getPath() + "   >> " +  u.getProtocol());
		}
		log.info("findTemplateSource " + entryPath);
		return "123456";
	}

	@Override
	public long getLastModified(Object templateSource) {
		log.info("findTemplateSource " + templateSource);
		return 0;
	}

	@Override
	public Reader getReader(Object templateSource, String encoding)
			throws IOException {
		log.info("getReader " + templateSource);
		return null;
	}

	@Override
	public void closeTemplateSource(Object templateSource) throws IOException {
		log.info("closeTemplateSource " + templateSource);
	}

}
