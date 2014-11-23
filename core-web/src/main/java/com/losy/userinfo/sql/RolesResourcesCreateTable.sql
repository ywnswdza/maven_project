DROP TABLE IF EXISTS `t_auth_roles_resources`;
CREATE TABLE `t_auth_roles_resources` (
		id INT(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
		roleId INTEGER(11) comment '角色',
		resourceId INTEGER(11) comment '资源'
);