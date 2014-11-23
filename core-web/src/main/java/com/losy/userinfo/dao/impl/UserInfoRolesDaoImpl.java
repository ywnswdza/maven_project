package com.losy.userinfo.dao.impl;

import org.springframework.stereotype.Repository;

import com.losy.userinfo.dao.IUserInfoRolesDao;
import com.losy.userinfo.domain.UserInfoRoles;
import com.losy.common.dao.impl.CommonDaoImpl;

/**
 * @daoimpl
 * @table t_sys_user_roles
 * @date 2014-05-15 11:27:41
 * @author losy
 */
@Repository("userInfoRolesDao")
public class UserInfoRolesDaoImpl extends CommonDaoImpl<UserInfoRoles> implements IUserInfoRolesDao{

	private String nameSpace = "com.losy.sql.sqlmapping.UserInfoRoles";
	
	public String getNameSpace() {
		return nameSpace;
	}

}
