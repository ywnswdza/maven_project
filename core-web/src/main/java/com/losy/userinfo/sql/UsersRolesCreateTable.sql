DROP TABLE IF EXISTS `t_auth_usersRoles`;
CREATE TABLE `t_auth_usersRoles` (
		id INT(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
		roleId INTEGER(11) comment '角色名称',
		userId INTEGER(11) comment '用户名称',
		isEnabled BOOLEAN  comment '是否有效',
		updateTime DATE  comment '创建时间',
		createTime DATE  comment '更新时间'
)