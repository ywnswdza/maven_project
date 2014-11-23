package com.losy.userinfo.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.losy.common.aop.PermissionMappingManager;
import com.losy.common.service.impl.CommonServiceImpl;
import com.losy.common.utils.SpringContextListener;
import com.losy.userinfo.dao.IResourcesDao;
import com.losy.userinfo.domain.Resources;
import com.losy.userinfo.domain.UserInfo;
import com.losy.userinfo.service.IResourcesService;

@Service("resourcesService")
public class ResourcesServiceImpl extends CommonServiceImpl<IResourcesDao,Resources> implements IResourcesService {

	
	@Resource(name="resourcesDao")
	public void setDao(IResourcesDao dao){
		this.dao = dao;
	}

	/**
	 * 根据主键执行insert或update
	 */
	public Resources save(Resources vo) {
		if(vo.getId() == null) {
			vo.setCreateTime(new Date());
			vo = this.insert(vo);
			if(vo.getParentId() != null && vo.getParentId() > 0)
			this.executeBySql("update t_sys_resources set isLeaf=false where id = " + vo.getParentId());
			SpringContextListener.getBean("permissionMappingManager",PermissionMappingManager.class).addURMapping(vo.getLinkUrl());
			return vo;
		}else { 
			vo.setUpdateTime(new Date());
			return this.update(vo);
		}
	}

	@Override
	public void deleteByVo(Resources vo) {
		if(vo.getId() == null) return;
		List<Integer> idList = new ArrayList<Integer>();
		findChildIdRecursionById(vo.getId(),idList);
//		log.info(idList);
		StringBuffer sql = new StringBuffer("delete from t_sys_resources where id in(");
		StringBuffer sqlM = new StringBuffer("delete from t_sys_roles_resources where resourceId in(");
		for (Integer id : idList) {
			sql.append(id).append(",");
			sqlM.append(id).append(",");
		}
		sql.deleteCharAt(sql.lastIndexOf(",")).append(");");
		sqlM.deleteCharAt(sqlM.lastIndexOf(",")).append(");");
		this.executeBySql(sql.toString());
		if(vo.getParentId() != null && vo.getParentId() > 0 && vo.getIsLeaf() == true) {
			this.executeBySql("update t_sys_resources set isLeaf=" + vo.getIsLeaf() + " where id = " + vo.getParentId());
		}
		this.executeBySql(sqlM.toString());
	}

	private void findChildIdRecursionById(Integer pid, List<Integer> idList) {
		if(pid == null) return;
		List<Map<String,Object>> result = this.selectListBySql("select id,parentId from t_sys_resources where parentId = " + pid);
		if(result != null)
		for (Map<String, Object> map : result) {
			Integer id = (Integer)map.get("id");
			findChildIdRecursionById(id,idList);
		}
		//删除自己
		idList.add(pid);
	}

	@Override
	public void moveMenu(Integer preId, Boolean preIsLeaf, String drapNodeIds,
			Integer dropNodeId,String drapNodePriority) {
		/** 移动到节点 **/
		Resources dropNode = this.getObjectById(dropNodeId);
		String[] nodeids = drapNodeIds.split(",");
		String[] prioritys = drapNodePriority.split(",");
		for(int i = 0;i < nodeids.length; i++ ) {
			/** 移动的顶层节点 **/
			String drapId = nodeids[i];
			Integer priority = prioritys[i] == null || "".equals(prioritys[i]) || "null".equals(prioritys[i]) ? 0 : Integer.parseInt(prioritys[i]);
			Integer drapNodeId = Integer.parseInt(drapId);
			Resources drapNode = this.getObjectById(drapNodeId);
			List<Integer> idList = new ArrayList<Integer>();
			findChildIdRecursionById(drapNodeId,idList);
			//int
			Integer qwDepth = dropNode.getDepth() + 1; //期望深度
			StringBuffer sql = new StringBuffer("update t_sys_resources set ");
			sql.append("depth=").append("depth+(").append(qwDepth - drapNode.getDepth()).append(")");
			
			sql.append(" where id in(");
			for (Integer id : idList) {
				sql.append(id).append(",");
			}
			sql.deleteCharAt(sql.lastIndexOf(",")).append(");");
			drapNode.setDepth(null);
			drapNode.setParentId(dropNode.getId());
			drapNode.setPriority(priority);
			this.update(drapNode);
			if(dropNode.getIsLeaf() == true )  {
				dropNode.setIsLeaf(false);
				this.update(dropNode);
			}
			this.executeBySql(sql.toString());
		}
		if(preIsLeaf == true) this.executeBySql("update t_sys_resources set isLeaf=" + preIsLeaf + " where id = " + preId);
	}

	@Override
	public Integer getLastPriorityByPid(Integer parentId) {
		if(parentId == null) parentId = 0;
		String sql = "select priority from t_sys_resources where parentId = " + parentId + " order by priority desc limit 1";
		Map<String,Object> result =	this.selectOneBysql(sql);
		if(result == null || !result.containsKey("priority")) return 1;
		return (Integer)result.get("priority") + 1;
	}
	
	public List<Resources> arrayToTree(List<Resources> sl){
		if(sl == null) return new ArrayList<Resources>();
		List<Resources> tagList = new ArrayList<Resources>();
		Map<Object,Object> records = new HashMap<Object, Object>();
		for (Resources r : sl) {
			records.put(r.getId(), r);
		}
		for (Resources cur : sl) {
			Resources p = null;
			if(!records.containsKey(cur.getParentId())) {
				tagList.add(cur);
				continue;
			} 
			p = (Resources)records.get(cur.getParentId());
			p.addChild(cur);
		}
		return tagList;
	}
	
	
	
	
	@Override
	public List<Resources> getMenuByUSER(UserInfo user,
			List<Resources> childMenu) {
		List<Resources> allM = dao.getMenuByUser(user);
		//user.addResource(allM);
		List<Resources> f = new ArrayList<Resources>();
		allM = this.arrayToTree(allM);
		for(Resources r : allM) {
			if(r.getChildren() != null) {
				childMenu.addAll(r.getChildren());
			} 
			f.add(r);
		}
		return f;
	}

	
	
	
	public <T> List<T> arrayToTree(List<T> list,String id,String pId){
		return Collections.emptyList();
	}
}
