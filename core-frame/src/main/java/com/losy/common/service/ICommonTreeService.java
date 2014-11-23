package com.losy.common.service;

import java.util.List;

import com.losy.common.domain.CommonTree;
import com.losy.common.service.ICommonService;
/**
 * @interfaceservice
 * @table t_common_tree
 * @date 2014-05-05 15:18:45
 * @author losy
 */
public interface ICommonTreeService extends ICommonService<CommonTree> {

	void deleteByVo(CommonTree vo);
	
	/**
	 * 移动树
	 * @param preId ,移动前的父ID
	 * @param preIsLeaf 移动前的父节点状态
	 * @param drapNodeId 移动的节点ID
	 * @param dropNodeId 移动到某节点下的节点ID
	 * @param drapNodePriority 
	 */
	void moveTree(Integer preId, Boolean preIsLeaf, String drapNodeIds,
			Integer dropNodeId, String drapNodePriority);

	List<CommonTree> selectTree(CommonTree tree);

	Integer getLastPriorityByPid(Integer pId);

}
