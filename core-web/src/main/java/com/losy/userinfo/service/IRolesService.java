package com.losy.userinfo.service;

import java.util.List;

import com.losy.common.service.ICommonService;
import com.losy.userinfo.domain.Roles;
import com.losy.userinfo.domain.UserInfo;
/**
 * @interfaceservice
 * @table t_sys_roles
 * @date 2014-05-14 11:20:20
 * @author losy
 */
public interface IRolesService extends ICommonService<Roles> {

	List<Roles> getListByUser(UserInfo vo);

}
