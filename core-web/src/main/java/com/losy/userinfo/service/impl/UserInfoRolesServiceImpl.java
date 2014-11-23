package com.losy.userinfo.service.impl;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import com.losy.userinfo.dao.IUserInfoRolesDao;
import com.losy.userinfo.domain.UserInfoRoles;
import com.losy.userinfo.service.IUserInfoRolesService;
import com.losy.common.service.impl.CommonServiceImpl;
import java.util.List;



/**
 * @serviceimpl
 * @table t_sys_user_roles
 * @date 2014-05-15 11:27:41
 * @author losy
 */
@Service("userInfoRolesService")
public class UserInfoRolesServiceImpl extends CommonServiceImpl<IUserInfoRolesDao,UserInfoRoles> implements IUserInfoRolesService {

	
	@Resource(name="userInfoRolesDao")
	public void setDao(IUserInfoRolesDao dao){
		this.dao = dao;
	}

	/**
	 * 根据主键执行insert或update
	 */
	public UserInfoRoles save(UserInfoRoles vo) {
		if(vo.getId() == null) {
			return this.insert(vo);
		} else {
			return this.update(vo);
		}
	}

	@Override
	public String updateURMapping(Integer userId, List<UserInfoRoles> urList) {
		this.executeBySql("delete from t_sys_user_roles where userId =" + userId);
		if(urList.size()>0)this.insertBatch(urList);
		return "操作成功";
	}

	@Override
	public String updateLinkUser(Integer roleId, List<UserInfoRoles> urList) {
		this.executeBySql("delete from t_sys_user_roles where roleId =" + roleId);
		if(urList.size()>0)this.insertBatch(urList);
		return "操作成功";
	}
	
}
