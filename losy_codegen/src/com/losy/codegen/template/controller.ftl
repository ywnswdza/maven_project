package ${packagePrefix}.${projectName}.controller;

import javax.annotation.Resource;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ${packagePrefix}.common.controller.CommonController;
import ${packagePrefix}.${projectName}.service.I${table.entitySimpleName}Service;
import ${packagePrefix}.${projectName}.domain.${table.entitySimpleName};

/**
 * @controller
 * @table ${table.tableName}
 * @date ${ddate}
 * @author ${author}
 */
@Controller("${table.entitySimpleName ? uncap_first}Controller")
@RequestMapping(value="/${colltrollerPath}")
public class ${table.entitySimpleName}Controller extends CommonController<I${table.entitySimpleName}Service,${table.entitySimpleName}>  {

	private static final String folder_name = "/${projectName}";

	@Resource(name="${table.entitySimpleName ? uncap_first}Service")
	public void setService(I${table.entitySimpleName}Service service) {
		this.service = service;
	}

	/** 子类实现编辑页面所在目录  */
	public String editHandler(Map<String, Object> result) {
		return folder_name + "/${table.entitySimpleName ? uncap_first}Edit";
	} 
	
	/** 子类实现列表页面所在目录  */
	protected String showListHandler(${table.entitySimpleName} ${table.entitySimpleName ? uncap_first},Map<String, Object> result) {
		
		return folder_name + "/${table.entitySimpleName ? uncap_first}List";
	} 
}
