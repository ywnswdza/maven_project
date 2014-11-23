package com.losy.userinfo.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.losy.common.service.impl.CommonServiceImpl;
import com.losy.common.utils.MD5Util;
import com.losy.common.utils.SpringContextListener;
import com.losy.common.utils.StrUtil;
import com.losy.userinfo.dao.IUserInfoDao;
import com.losy.userinfo.domain.Roles;
import com.losy.userinfo.domain.UserInfo;
import com.losy.userinfo.service.IRolesService;
import com.losy.userinfo.service.IUserInfoService;

@Service("userInfoService")
public class UserInfoServiceImpl extends CommonServiceImpl<IUserInfoDao,UserInfo> implements IUserInfoService {

	
	@Resource(name="userInfoDao")
	public void setDao(IUserInfoDao dao){
		this.dao = dao;
	}

	@Autowired
	private IRolesService rService;
	
	/**
	 * 根据主键执行insert或update
	 */
	public UserInfo save(UserInfo vo) {
		if(vo.getUserId() == null) {
			vo.setCreateTime(new Date());
			//vo.getPassword();
			vo.setPassword(MD5Util.calc(vo.getPassword()));
			return this.insert(vo);
		} else {
			vo.setUpdateTime(new Date());
			return this.update(vo);
		}
	}

	public UserInfo getUsersByUsername(String username) {
		if(StrUtil.isNullOrEmpty(username)) return null;
		UserInfo vo = new UserInfo();
		//vo.setUserAccount(username);
		vo.setAppendWhere(" and useraccount ='" + username + "'");
		List<UserInfo> list = dao.getListLimit(vo,0,1);
		vo = list != null && list.size() > 0 ? list.get(0) : null;
		if(vo != null) {
			List<Roles> roles = rService.getListByUser(vo);
			vo.setRoles(roles);
		}
		return vo;
	}

	@Override
	public void removeByIds(String ids) {
		super.removeByIds(ids);
		this.executeBySql("delete from t_sys_user_roles where userId in (" + ids + ")");
	}

	@Override
	public void resetPwd(String ids) {
		if(StrUtil.isNullOrEmpty(ids)) return;
		String pwd = SpringContextListener.getContextProValue("resetPasswrod", MD5Util.calc("xxwan123456"));
		this.executeBySql("update t_sys_userinfo set password = '" + pwd + "' where userId in(" + ids + ")");
	}

	
}
