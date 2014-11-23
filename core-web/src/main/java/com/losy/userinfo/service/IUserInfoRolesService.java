package com.losy.userinfo.service;

import java.util.List;

import com.losy.userinfo.domain.UserInfoRoles;
import com.losy.common.service.ICommonService;
/**
 * @interfaceservice
 * @table t_sys_user_roles
 * @date 2014-05-15 11:27:41
 * @author losy
 */
public interface IUserInfoRolesService extends ICommonService<UserInfoRoles> {

	String updateURMapping(Integer userId, List<UserInfoRoles> urList);

	String updateLinkUser(Integer roleId, List<UserInfoRoles> urList);

}
