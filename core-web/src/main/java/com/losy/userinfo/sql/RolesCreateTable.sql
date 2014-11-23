DROP TABLE IF EXISTS `t_auth_roles`;
CREATE TABLE `t_auth_roles` (
		roleId INT(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
		roleName VARCHAR(20) default '' comment '角色名称',
		roleDesc VARCHAR(100) default '' comment '角色描述',
		isEnabled BOOLEAN  comment '是否有效',
		updateTime DATE  comment '更新时间',
		createTime DATE  comment '创建时间'
);