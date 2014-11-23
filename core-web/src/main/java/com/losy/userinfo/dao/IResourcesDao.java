package com.losy.userinfo.dao;

import java.util.List;

import com.losy.common.dao.ICommonDao;
import com.losy.userinfo.domain.Resources;
import com.losy.userinfo.domain.UserInfo;

public interface IResourcesDao extends ICommonDao<Resources> {

	List<Resources> getMenuByUser(UserInfo user);

}