package com.losy.userinfo.service.impl;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import com.losy.userinfo.dao.IUsersRolesDao;
import com.losy.userinfo.domain.UsersRoles;
import com.losy.userinfo.service.IUsersRolesService;
import com.losy.common.service.impl.CommonServiceImpl;

@Service("usersRolesService")
public class UsersRolesServiceImpl extends CommonServiceImpl<IUsersRolesDao,UsersRoles> implements IUsersRolesService {

	
	@Resource(name="usersRolesDao")
	public void setDao(IUsersRolesDao dao){
		this.dao = dao;
	}

	/**
	 * 根据主键执行insert或update
	 */
	public UsersRoles save(UsersRoles vo) {
		if(vo.getId() == null) 
			return this.insert(vo);
		else 
			return this.update(vo);
	}
	
}
