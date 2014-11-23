package com.losy.userinfo.dao;

import java.util.List;

import com.losy.common.dao.ICommonDao;
import com.losy.userinfo.domain.Roles;
import com.losy.userinfo.domain.UserInfo;
/**
 * @intefacedao
 * @table t_sys_roles
 * @date 2014-05-14 11:20:20
 * @author losy
 */
public interface IRolesDao extends ICommonDao<Roles> {

	List<Roles> getListByUser(UserInfo vo);

}