package com.losy.codegen.core;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;


import com.losy.codegen.domian.TableInfo;
import com.losy.codegen.utils.ReadPropertiesUtil;
import com.losy.codegen.utils.StringUtils;
import com.losy.codegen.utils.TemplateUtils;

import freemarker.template.Template;

public class CodeGenerator {

	private static final Logger log = Logger.getLogger(CodeGenerator.class);
	private static StringBuffer outputRootPath = new StringBuffer(ReadPropertiesUtil.getValue("app.output",System.getProperty("user.dir")));
	private static String packagePrefix = ReadPropertiesUtil.getValue("app.packagePrefix");
	private static String projectName = ReadPropertiesUtil.getValue("app.projectName");
	private static String namespace = ReadPropertiesUtil.getValue("colltroller.namespace","codegenerator");
	private static String author = ReadPropertiesUtil.getValue("app.author","codegenerator");
	
	static {
		TemplateUtils.initTempConfig("../template");
	}
	
	public static void generatorAll(List<TableInfo> tableInfos){
		String datestr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		for (TableInfo tableInfo : tableInfos) {
			Map<String,Object> m = new HashMap<String, Object>();
			m.put("table", tableInfo);
			m.put("ddate", datestr);
			m.put("packagePrefix", packagePrefix);
			m.put("projectName", projectName);
			m.put("author", author);
			if(ReadPropertiesUtil.getBooleanValue("gen.sql")) {
				m.put("outPath", outputRootPath + File.separator + "src" + File.separator + packagePrefix.replaceAll("\\.", "\\\\") + File.separator + projectName + File.separator + "sql");
				m.put("fileName", tableInfo.getEntitySimpleName() + "CreateTable.sql");
				generator(m,"createtable.ftl");
				CreateTableUtils.addFile(m.get("outPath") + File.separator + m.get("fileName"));
				m.put("fileName", tableInfo.getEntitySimpleName() + "SqlMapping.xml");
				log.debug("generator " + tableInfo.getEntityName() + " sql mapping  ...... ");
				generator(m,"sqlMapping.ftl");
			}
			if(ReadPropertiesUtil.getBooleanValue("gen.domian")) {
				m.put("outPath", outputRootPath + File.separator + "src" + File.separator + packagePrefix.replaceAll("\\.", "\\\\") + File.separator + projectName + File.separator + "domain");
				m.put("fileName", tableInfo.getEntitySimpleName() + ".java");
				
				log.debug("generator " + tableInfo.getEntityName() + " domain object ...... ");
				generator(m,"domain.ftl");
			}
			if(ReadPropertiesUtil.getBooleanValue("gen.dao")) {
				m.put("outPath", outputRootPath + File.separator + "src" + File.separator + packagePrefix.replaceAll("\\.", "\\\\") + File.separator + projectName + File.separator + "dao");
				m.put("fileName","I" + tableInfo.getEntitySimpleName() + "Dao.java");
				log.debug("generator " + tableInfo.getEntityName() + " interface dao  ...... ");
				generator(m,"interfacedao.ftl");
				
				m.put("outPath",m.get("outPath") + File.separator + "impl");
				log.debug("generator " + tableInfo.getEntityName() + " interface dao  implements ...... ");
				m.put("fileName",tableInfo.getEntitySimpleName() + "DaoImpl.java");
				generator(m,"daoimpl.ftl");
			}
			
			if(ReadPropertiesUtil.getBooleanValue("gen.service")) {
				m.put("outPath", outputRootPath + File.separator + "src" + File.separator + packagePrefix.replaceAll("\\.", "\\\\") + File.separator + projectName + File.separator + "service");
				m.put("fileName","I" + tableInfo.getEntitySimpleName() + "Service.java");
				log.debug("generator " + tableInfo.getEntityName() + " interface service ...... ");
				generator(m,"interfaceservice.ftl");
				
				m.put("outPath",m.get("outPath") + File.separator + "impl");
				m.put("fileName",tableInfo.getEntitySimpleName() + "ServiceImpl.java");
				log.debug("generator " + tableInfo.getEntityName() + " interface service implements ...... ");
				generator(m,"serviceimpl.ftl");
			}
			
			String colltrollerPath = namespace + "/" + StringUtils.uncapfirst(tableInfo.getEntitySimpleName()); 
			m.put("colltrollerPath", colltrollerPath);
			if(ReadPropertiesUtil.getBooleanValue("gen.controller")) {
				m.put("outPath", outputRootPath  + File.separator + "src"+File.separator + packagePrefix.replaceAll("\\.", "\\\\") + File.separator + projectName + File.separator + "controller");
				m.put("fileName",tableInfo.getEntitySimpleName() + "Controller.java");
				log.debug("generator " + tableInfo.getEntityName() + " Controller  ...... ");
				generator(m,"controller.ftl");
			}
			
			if(ReadPropertiesUtil.getBooleanValue("gen.jsp")) {
				m.put("outPath", outputRootPath  + File.separator + "WebRoot" + File.separator + projectName);
				m.put("fileName",StringUtils.uncapfirst(tableInfo.getEntitySimpleName()) + "List.jsp");
				log.debug("generator " + tableInfo.getEntityName() + " List.jsp  ...... ");
				
				if(ReadPropertiesUtil.getBooleanValue("jsp.easyui"))  generator(m,"pagelistEasyUI.ftl");
				else generator(m,"pagelist.ftl");
				
				m.put("fileName",StringUtils.uncapfirst(tableInfo.getEntitySimpleName()) + "Edit.jsp");
				log.debug("generator " + tableInfo.getEntityName() + " Edit.jsp ...... ");
				if(ReadPropertiesUtil.getBooleanValue("jsp.easyui"))  generator(m,"editEasyUI.ftl");
				else generator(m,"edit.ftl");
			}
		}
		if(ReadPropertiesUtil.getBooleanValue("gen.createTable")) {
			CreateTableUtils.createTables();
		}
	}
	
	/**
	 * @param tableInfo
	 * @param moduleName 模块名称，如 action , service ,dao ,sql 
	 * @param templateName 模板名称
	 * @param fileNameSuffix 文件名后缀  如 .java  .xml  SqlMapping.xml
	 */
	public static void generator(Map<String,Object> rootMap,String templateName) {
		StringBuffer outBuf = new StringBuffer(rootMap.get("outPath").toString());
		String templatePath = File.separator + templateName;
		Writer wt = null;
		try {
			File file = new File(outBuf.toString());
			if(!file.exists()) file.mkdirs();
			File javaFile = new File(file,rootMap.get("fileName").toString());
			if(!javaFile.exists() || ReadPropertiesUtil.getBooleanValue("gen.cover")) {
				wt = new FileWriter(javaFile);
				Template entity = TemplateUtils.getTemplate(templatePath);
				entity.process(rootMap, wt);
				return;
			}
			log.info(javaFile.getName() +  " exists,ignore to continue execute .......");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		} finally {
			if(wt != null) {
				try {
					wt.flush();
					wt.close();
				} catch (IOException e) {
					log.error(e);
				}
			}
		}
	}
	
//	public static void main(String[] args) {
//		List<TableInfo> result = new MyAnnotationParseImpl().parse(Test.class);
//		CodeGenerator.generatorAll(result);
//	}
	
	public static void main(String[] args) {
//		run(test.UserInfo.class);
//		System.out.println(args.length);
		if(args.length == 0) {
			log.info("no parms in,app exit ......");
			System.exit(0);
			return;
		}
		List<Class<?>> classArray = new ArrayList<Class<?>>();
		for (int i = 0 ;i < args.length; i++) {
			String argsP = "";
			try {
				argsP = args[i];
				Class<?> clazz = Class.forName(argsP);
				classArray.add(clazz);
			} catch (ClassNotFoundException e) {
				log.error("clazz " + argsP + " not found , please checked your config .... ");
			}
		}
		Class<?> [] params = new Class<?>[classArray.size()];
//		run(classArray.toArray(params));
		generatorAll(new AnnotationParser().parse(classArray.toArray(params)));
//		run(UserInfo.class);
	}
}
