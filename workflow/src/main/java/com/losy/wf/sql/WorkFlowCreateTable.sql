DROP TABLE IF EXISTS `t_wf_workflow`;
CREATE TABLE `t_wf_workflow` (
		id INT(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
		flowName VARCHAR(100) default '' comment '流程名称',
		createName VARCHAR(30) default '' comment '创建人',
		descr VARCHAR(999) default '' comment '描述'
);