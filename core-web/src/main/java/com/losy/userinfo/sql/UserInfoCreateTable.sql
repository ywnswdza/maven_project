DROP TABLE IF EXISTS `t_userInfo`;
CREATE TABLE `t_userInfo` (
	userId INT(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
	userAccount VARCHAR(30)  default ''  comment '登录帐号',
	password VARCHAR(50)  default ''  comment '用户密码',
	username VARCHAR(20)  default ''  comment '用户名称',
	isEnabled BOOLEAN comment '是否有效',
	isSupperUser BOOLEAN comment '是否超级用户',
	userDesc VARCHAR(100)  default ''  comment '用户描述 ',
	updateTime DATE comment '创建时间',
	createTime DATE comment '更新时间'
)