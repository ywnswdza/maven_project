package com.losy.common.controller;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.losy.common.service.ICommonService;
import com.losy.common.utils.Page;

/**
 * spring mvc controller crud抽象类
 * @param <T> 实体
 * @param <R> 实体对应的 service层接口
 * @author longyt
 */
public abstract class CommonController<R extends ICommonService<T>,T> {

	protected final static Logger log = Logger.getLogger(CommonController.class); 
	
	protected R service;
	
	//private Class<T> entityClass;
	private Class<R> serviceClass;
	
	/**
	 * spring mvc 获取 HttpServletRequest
	 * @return HttpServletRequest
	 */
	protected HttpServletRequest getRequest(){
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		return request;
	}
	
	/**
	 * 保存对象方法
	 * @param vo
	 * @return
	 */
	@RequestMapping("/save")
	@ResponseBody
	public Object save(T vo){
		beforeSave(vo);
		log.debug("CommonController execute method save() , call " + serviceClass.getSimpleName()  + ".save() method .....");
		if(vo != null) {
			vo = service.save(vo);
		}
		return vo;
	}
	
	/**
	 * 跳转到 修改或新增页面
	 * @param id 对象的 标识
	 * @param result  , 作为返回的参数
	 * @return
	 */
	@RequestMapping("/edit")
	public String edit(Integer id,Map<String,Object> result){
		log.debug("CommonController execute method edit() , call " + serviceClass.getSimpleName()  + ".getObjectById() method .....");
		if(id != null) {
			T vo = service.getObjectById(id);
			result.put("vo", vo);
		}
		return editHandler(result);
	}
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/editV")
	public String editByVo(T vo,Integer id,Map<String,Object> result) {
		result.put("vo", vo);
		String jsp = edit(id, result);
		AfterEditByVo((T)result.get("vo"),result);
		return jsp;
	}
	

	/**
	 * 分页查询
	 * @param page ,接收 pageNo,pageSize参数
	 * @param vo ,接收 vo的属性参数作为条件查询
	 * @return page >>>>>>>> json
	 */
	@RequestMapping("/listData")
	@ResponseBody
	public Object listData(Page<T> page,T vo) {
		log.debug("CommonController execute method selectList() , call " + serviceClass.getSimpleName()  + ".getListByPage() method .....");
		beforeListData(vo);
		page = service.getListByPage(vo, page.getPageNo(), page.getPageSize());
		return page;
	}


	/**
	 * 显示列表页面
	 * @param vo ,接收 vo的属性参数作为条件查询
	 * @return page >>>>>>>> list 页面，由子类实现
	 */
	@RequestMapping("/showList")
	public String showList(T vo,Map<String,Object> result) {
//		page = service.getListByPage(vo, page.getPageNo(), page.getPageSize());
//		result.put("page", page);
		return showListHandler(vo,result);
	}
	
	/**
	 * 根据ids删除表数据
	 * @param ids , 表数据id，多个用,号隔开
	 * @return json
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Object delete(String ids) {
		beforeDelete(ids);
		log.debug("CommonController execute method delete() , call " + serviceClass.getSimpleName()  + ".removeByIds() method .....");
		Map<String,Object> json = new HashMap<String, Object>();
		service.removeByIds(ids);
		json.put("status", true);
		return json;
	}
	
	@SuppressWarnings("unchecked")
	public CommonController(){
		Type [] trueType = ((ParameterizedType)this.getClass().getGenericSuperclass()).getActualTypeArguments();
		//this.entityClass  = (Class<T>)trueType[1];
		this.serviceClass = (Class<R>)trueType[0];
		log.debug("spring mvc init " + this.getClass().getSimpleName() + " .......");
	}
	
	/** 使用注解或其它方法，初始化service **/
	protected abstract void setService(R service);
	/** 返回编辑页面的全路径 ,不用别 .jsp后缀 **/
	protected abstract String editHandler(Map<String,Object> result);
	
	/**
	 * 返回列表页面  
	 * @param vo 传入参数
	 * @param result 传出参数
	 * @return
	 */
	protected abstract String showListHandler(T vo,Map<String,Object> result);
	/**
	 * 保存前处理,如果子类重写了，则调用子类的 
	 * @param vo 传入参数
	 */
	protected void beforeSave(T vo) {}
	/**
	 * 删除前处理,如果子类重写了，则调用子类的
	 * @param ids   逗号隔开，例 112,231,2321
	 */
	protected void beforeDelete(String ids){}
	/**
	 * 查徇分页数据前处理,如果子类重写了，则调用子类的
	 * @param vo 传入参数
	 */
	protected void beforeListData(T vo) {}
	
	protected void AfterEditByVo(T vo,Map<String, Object> result) {}
	
/*	protected UserInfo getCurrentLoginUser(){
		UserInfo user = null;
		user = (UserInfo) getRequest().getSession().getAttribute(SessionKeys.USER);
		return user;
	}*/
}

