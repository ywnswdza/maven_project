DROP TABLE IF EXISTS `t_sys_user_roles`;
CREATE TABLE `t_sys_user_roles` (
		id INT(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
		roleId INTEGER(11) comment '角色名称',
		userId INTEGER(11) comment '用户名称'
);