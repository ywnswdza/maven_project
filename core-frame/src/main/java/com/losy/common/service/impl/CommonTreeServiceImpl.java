package com.losy.common.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.losy.common.dao.ICommonTreeDao;
import com.losy.common.domain.CommonTree;
import com.losy.common.service.ICommonTreeService;
import com.losy.common.service.impl.CommonServiceImpl;

/**
 * @serviceimpl
 * @table t_common_tree
 * @date 2014-05-05 15:18:45
 * @author losy
 */
@Service("commonTreeService")
public class CommonTreeServiceImpl extends CommonServiceImpl<ICommonTreeDao,CommonTree> implements ICommonTreeService {

	
	@Resource(name="commonTreeDao")
	public void setDao(ICommonTreeDao dao){
		this.dao = dao;
	}

	/**
	 * 根据主键执行insert或update
	 */
	public CommonTree save(CommonTree vo) {
		if(vo.getId() == null) {
			vo.setCreateTime(new Date());
			vo = this.insert(vo);
			if(vo.getPId() != null && vo.getPId() > 0) {
				this.executeBySql("update t_common_tree set isLeaf = false where id = " + vo.getPId());
			}
			return vo;
		} else 
			vo.setUpdateTime(new Date());
			return this.update(vo);
	}

	
	@Override
	public void deleteByVo(CommonTree vo) {
		if(vo.getId() == null) return;
		List<Integer> idList = new ArrayList<Integer>();
		findChildIdRecursionById(vo.getId(),idList);
		log.info(idList);
		StringBuffer sql = new StringBuffer("delete from t_common_tree where id in(");
		for (Integer id : idList) {
			sql.append(id).append(",");
		}
		sql.deleteCharAt(sql.lastIndexOf(",")).append(");");
		this.executeBySql(sql.toString());
		if(vo.getPId() != null && vo.getPId() > 0 && vo.getIsLeaf() == true) {
			this.executeBySql("update t_common_tree set isLeaf=" + vo.getIsLeaf() + " where id = " + vo.getPId());
		}
	}
	
	
	
	/**
	 * 
	 */
	@Override
	public void moveTree(Integer preId, Boolean preIsLeaf, String drapNodeIds,
			Integer dropNodeId,String drapNodePriority) {
		/** 移动到节点 **/
		CommonTree dropNode = this.getObjectById(dropNodeId);
		String[] nodeids = drapNodeIds.split(",");
		String[] prioritys = drapNodePriority.split(",");
		for(int i = 0;i < nodeids.length; i++ ) {
			/** 移动的顶层节点 **/
			String drapId = nodeids[i];
			Integer priority = prioritys[i] == null || "".equals(prioritys[i]) || "null".equals(prioritys[i]) ? 0 : Integer.parseInt(prioritys[i]);
			Integer drapNodeId = Integer.parseInt(drapId);
			CommonTree drapNode = this.getObjectById(drapNodeId);
			List<Integer> idList = new ArrayList<Integer>();
			findChildIdRecursionById(drapNodeId,idList);
			//int
			Integer qwDepth = dropNode.getDepth() + 1; //期望深度
			StringBuffer sql = new StringBuffer("update t_common_tree set ");
			sql.append("depth=").append("depth+(").append(qwDepth - drapNode.getDepth()).append(")");
			if(drapNode.getKindId() != dropNode.getKindId()) {
				drapNode.setKindId(dropNode.getKindId());
				sql.append(",kindId=").append(dropNode.getKindId());
			}
			sql.append(" where id in(");
			for (Integer id : idList) {
				sql.append(id).append(",");
			}
			sql.deleteCharAt(sql.lastIndexOf(",")).append(");");
			
			drapNode.setDepth(null);
			drapNode.setPId(dropNode.getId());
			drapNode.setPriority(priority);
			this.update(drapNode);
			if(dropNode.getIsLeaf() == true )  {
				dropNode.setIsLeaf(false);
				this.update(dropNode);
			}
			this.executeBySql(sql.toString());
		}
		if(preIsLeaf == true) this.executeBySql("update t_common_tree set isLeaf=" + preIsLeaf + " where id = " + preId);
	}

	/**
	 * 递归查找子节点ID
	 * @param pid
	 * @param idList
	 */
	private void findChildIdRecursionById(Integer pid,List<Integer> idList) {
		if(pid == null) return;
		List<Map<String,Object>> result = this.selectListBySql("select id,pid from t_common_tree where pId = " + pid);
		if(result != null)
		for (Map<String, Object> map : result) {
			Integer id = (Integer)map.get("id");
			findChildIdRecursionById(id,idList);
		}
		//删除自己
		idList.add(pid);
	}

	@Override
	public List<CommonTree> selectTree(CommonTree tree) {
		if(tree.getPId() == null) tree.setPId(0);
		return getList(tree);
	}

	@Override
	public Integer getLastPriorityByPid(Integer parentId) {
		if(parentId == null) parentId = 0;
		String sql = "select priority from t_common_tree where pId = " + parentId + " order by priority desc limit 1";
		Map<String,Object> result =	this.selectOneBysql(sql);
		if(result == null || !result.containsKey("priority")) return 1;
		return (Integer)result.get("priority") + 1;
	}
}
