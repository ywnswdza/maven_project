package com.losy.userinfo.service;

import java.util.List;

import com.losy.userinfo.domain.RolesResources;
import com.losy.common.service.ICommonService;
/**
 * @interfaceservice
 * @table t_sys_roles_resources
 * @date 2014-05-14 11:50:54
 * @author losy
 */
public interface IRolesResourcesService extends ICommonService<RolesResources> {

	void insertBatchByRolesId(List<RolesResources> rrsList, Integer rolesId);

	String updateRRMapping(Integer key, List<RolesResources> urList);

}
