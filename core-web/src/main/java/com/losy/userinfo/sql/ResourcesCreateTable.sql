DROP TABLE IF EXISTS `t_auth_resources`;
CREATE TABLE `t_auth_resources` (
		id INT(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
		name VARCHAR(15) default '' comment '资源名称',
		type INTEGER(11) comment '1:顶级菜单,2:url,3:method',
		linkUrl VARCHAR(150) default '' comment '资源链接',
		priority INTEGER(11) comment '优先级',
		isEnabled BOOLEAN  comment '是否有效',
		parentId INTEGER(11) comment '父级目录',
		`desc` VARCHAR(150) default '' comment '描述',
		isLeaf BOOLEAN  comment '是否子节点',
		depth TINYINT(4) comment '层级',
		updateTime DATE  comment '创建时间',
		createTime DATE  comment '更新时间'
)