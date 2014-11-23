package com.losy.userinfo.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.losy.common.dao.impl.CommonDaoImpl;
import com.losy.userinfo.dao.IResourcesDao;
import com.losy.userinfo.domain.Resources;
import com.losy.userinfo.domain.UserInfo;
@Repository("resourcesDao")
public class ResourcesDaoImpl extends CommonDaoImpl<Resources> implements IResourcesDao{

	private String nameSpace = "com.losy.sql.sqlmapping.Resources";
	

	public String getNameSpace() {
		return nameSpace;
	}


	@Override
	public List<Resources> getMenuByUser(UserInfo user) {
		return this.getSqlSession().selectList(getNameSpace().concat(".getMenuByUser"), user);
	}

}
