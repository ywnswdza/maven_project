package com.losy.userinfo.service.impl;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import com.losy.userinfo.dao.IRolesResourcesDao;
import com.losy.userinfo.domain.RolesResources;
import com.losy.userinfo.service.IRolesResourcesService;
import com.losy.common.service.impl.CommonServiceImpl;
import java.util.List;



/**
 * @serviceimpl
 * @table t_sys_roles_resources
 * @date 2014-05-14 11:50:54
 * @author losy
 */
@Service("rolesResourcesService")
public class RolesResourcesServiceImpl extends CommonServiceImpl<IRolesResourcesDao,RolesResources> implements IRolesResourcesService {

	
	@Resource(name="rolesResourcesDao")
	public void setDao(IRolesResourcesDao dao){
		this.dao = dao;
	}

	/**
	 * 根据主键执行insert或update
	 */
	public RolesResources save(RolesResources vo) {
		if(vo.getId() == null) {
			return this.insert(vo);
		} else {
			return this.update(vo);
		}
	}

	public void insertBatchByRolesId(List<RolesResources> rrsList,
			Integer rolesId) {
		this.executeBySql("delete from t_sys_roles_resources where roleid = " + rolesId);
		this.insertBatch(rrsList);
	}

	public String updateRRMapping(Integer key, List<RolesResources> urList) {
		this.executeBySql("delete from t_sys_roles_resources where resourceId =" + key);
		if(urList.size()>0)this.insertBatch(urList);
		return "操作成功";
	}
	
}
