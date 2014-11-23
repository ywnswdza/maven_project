DROP TABLE IF EXISTS `t_common_tree`;
CREATE TABLE `t_common_tree` (
		id INT(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
		pId INTEGER(11) comment '父节点',
		depth TINYINT(4) comment '层级',
		nodeText VARCHAR(30) default '' comment '节点名称',
		kindId INTEGER(11) comment '种类',
		isValid BOOLEAN  comment '是否有效',
		isLeaf BOOLEAN  comment '是否子节点',
		`desc` VARCHAR(255) default '' comment '描述',
		priority INTEGER(11) comment '优先级',
		updateTime DATE  comment '更新时间',
		createTime DATE  comment '创建时间'
);