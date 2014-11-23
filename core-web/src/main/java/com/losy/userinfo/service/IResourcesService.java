package com.losy.userinfo.service;

import java.util.List;

import com.losy.common.service.ICommonService;
import com.losy.userinfo.domain.Resources;
import com.losy.userinfo.domain.UserInfo;

public interface IResourcesService extends ICommonService<Resources> {

	void deleteByVo(Resources vo);

	void moveMenu(Integer preId, Boolean preIsLeaf, String drapNodeIds,
			Integer dropNodeId, String drapNodePriority);

	Integer getLastPriorityByPid(Integer parentId);
	
	public List<Resources> arrayToTree(List<Resources> sl);

	List<Resources> getMenuByUSER(UserInfo user, List<Resources> childMenu);

}
