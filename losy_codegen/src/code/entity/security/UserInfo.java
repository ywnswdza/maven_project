package code.entity.security;

import java.util.Date;

import com.losy.codegen.annotation.Column;
import com.losy.codegen.annotation.JdbcType;
import com.losy.codegen.annotation.Table;

@Table(name="t_sys_userinfo")
public class UserInfo  {
	
	/*1 User_Id Vchar(32) 用户id PK 
	2 user_account Vchar(30) 登陆用户名(登陆号)  
	3 User_name Vchar(40) 用户姓名  
	4 user_Password Vchar(100) 用户密码  5 Enabled Int 是否被禁用 0禁用1正常 
	6 isSys Int 是否是超级用户 0非1是 
	7 user_DESc Vchar(100) 描述 */

	@Column(id=true)
	public Integer userId;
	
	//登录帐号
	@Column(label="登录帐号",length=30)
	public String userAccount; 
	
	@Column(label="用户密码",length=50)
	public String password;// = ConstantUtil.USER_DEFAULT_PASSWORD;

	@Column(label="用户名称",length=20)
	public String username;  //用户名称
	
	@Column(label="是否有效",jdbcType=JdbcType.BOOLEAN)
	public Boolean isEnabled; //0禁用，1正常
	
	@Column(label="是否超级用户",jdbcType=JdbcType.BOOLEAN)
	public Boolean isSupperUser;// = false; //是否超级用户,0否，1是
	
	@Column(label="用户描述 ",length=100)
	public String userDesc; //用户描述 
	@Column(label="创建时间",jdbcType=JdbcType.DATE)
	public Date updateTime;
	@Column(label="更新时间",jdbcType=JdbcType.DATE)
	public Date createTime;

}
