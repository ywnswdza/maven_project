package com.losy.userinfo.dao.impl;

import org.springframework.stereotype.Repository;

import com.losy.userinfo.dao.IRolesResourcesDao;
import com.losy.userinfo.domain.RolesResources;
import com.losy.common.dao.impl.CommonDaoImpl;

/**
 * @daoimpl
 * @table t_sys_roles_resources
 * @date 2014-05-14 11:50:54
 * @author losy
 */
@Repository("rolesResourcesDao")
public class RolesResourcesDaoImpl extends CommonDaoImpl<RolesResources> implements IRolesResourcesDao{

	private String nameSpace = "com.losy.sql.sqlmapping.RolesResources";
	
	public String getNameSpace() {
		return nameSpace;
	}

}
