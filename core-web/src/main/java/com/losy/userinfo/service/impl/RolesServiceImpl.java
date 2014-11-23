package com.losy.userinfo.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.losy.common.service.impl.CommonServiceImpl;
import com.losy.userinfo.dao.IRolesDao;
import com.losy.userinfo.domain.Roles;
import com.losy.userinfo.domain.UserInfo;
import com.losy.userinfo.service.IRolesService;


/**
 * @serviceimpl
 * @table t_sys_roles
 * @date 2014-05-14 11:20:20
 * @author losy
 */
@Service("rolesService")
public class RolesServiceImpl extends CommonServiceImpl<IRolesDao,Roles> implements IRolesService {

	
	@Resource(name="rolesDao")
	public void setDao(IRolesDao dao){
		this.dao = dao;
	}

	/**
	 * 根据主键执行insert或update
	 */
	public Roles save(Roles vo) {
		if(vo.getRoleId() == null) {
			vo.setCreateTime(new Date());
			return this.insert(vo);
		} else {
			vo.setUpdateTime(new Date());
			return this.update(vo);
		}
	}

	@Override
	public void removeByIds(String ids) {
		super.removeByIds(ids);
		List<String> sqlList = new ArrayList<String>();
		sqlList.add("delete from t_sys_user_roles where roleId in (" + ids + ");");
		sqlList.add("delete from t_sys_roles_resources where roleId in (" + ids + ");");
		this.executeBatchSql(sqlList);
	}

	@Override
	public List<Roles> getListByUser(UserInfo vo) {
		return dao.getListByUser(vo);
	}
	
	
}
