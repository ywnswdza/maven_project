
DROP TABLE IF EXISTS `t_attachment`;
CREATE TABLE `t_attachment` (
	`id` INT(11) PRIMARY KEY AUTO_INCREMENT,
	`fileRealName` VARCHAR(255),
	`fileName` VARCHAR(255),
	`fileFolder` VARCHAR(255),
	`fileSize` DOUBLE(11,4),
	`uploadName` VARCHAR(255),
	`groupId` VARCHAR(255),
	`createTime` DATETIME
)