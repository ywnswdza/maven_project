package com.losy.userinfo.service;

import com.losy.common.service.ICommonService;
import com.losy.userinfo.domain.UserInfo;

public interface IUserInfoService extends ICommonService<UserInfo> {

	UserInfo getUsersByUsername(String username);

	void resetPwd(String ids);

}
