package com.losy.userinfo.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.losy.common.dao.impl.CommonDaoImpl;
import com.losy.userinfo.dao.IRolesDao;
import com.losy.userinfo.domain.Roles;
import com.losy.userinfo.domain.UserInfo;

/**
 * @daoimpl
 * @table t_sys_roles
 * @date 2014-05-14 11:20:20
 * @author losy
 */
@Repository("rolesDao")
public class RolesDaoImpl extends CommonDaoImpl<Roles> implements IRolesDao{

	private String nameSpace = "com.losy.sql.sqlmapping.Roles";
	
	public String getNameSpace() {
		return nameSpace;
	}

	@Override
	public List<Roles> getListByUser(UserInfo vo) {
		return this.getSqlSession().selectList(getNameSpace().concat(".getListByUser"), vo);
	}

}
