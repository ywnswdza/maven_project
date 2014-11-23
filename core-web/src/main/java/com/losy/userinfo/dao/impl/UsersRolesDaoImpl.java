package com.losy.userinfo.dao.impl;

import org.springframework.stereotype.Repository;

import com.losy.userinfo.dao.IUsersRolesDao;
import com.losy.userinfo.domain.UsersRoles;
import com.losy.common.dao.impl.CommonDaoImpl;
@Repository("usersRolesDao")
public class UsersRolesDaoImpl extends CommonDaoImpl<UsersRoles> implements IUsersRolesDao{

	private String nameSpace = "com.losy.sql.sqlmapping.UsersRoles";
	

	public String getNameSpace() {
		return nameSpace;
	}

}
