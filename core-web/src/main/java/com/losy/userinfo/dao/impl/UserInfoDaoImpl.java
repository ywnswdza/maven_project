package com.losy.userinfo.dao.impl;

import org.springframework.stereotype.Repository;

import com.losy.common.dao.impl.CommonDaoImpl;
import com.losy.userinfo.dao.IUserInfoDao;
import com.losy.userinfo.domain.UserInfo;
@Repository("userInfoDao")
public class UserInfoDaoImpl extends CommonDaoImpl<UserInfo> implements IUserInfoDao{

	private String nameSpace = "com.losy.sql.sqlmapping.UserInfo";
	

	public String getNameSpace() {
		return nameSpace;
	}

}
