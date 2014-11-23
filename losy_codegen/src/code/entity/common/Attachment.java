package code.entity.common;

import java.util.Date;

import com.losy.codegen.annotation.Column;
import com.losy.codegen.annotation.JdbcType;
import com.losy.codegen.annotation.Table;

/**
 * @entity
 * @table t_
 * @date 
 * @author codegen
 */
@Table(name="t_common_attachment")
@SuppressWarnings("unused")
public class Attachment {
	
	@Column(name="id",id=true,jdbcType=JdbcType.INTEGER)
	private Integer id;
	
	@Column(name="fileRealName",comment="上传时文件名",length=100,label="真实名")
	private String fileRealName; //上传时文件名
	
	@Column(name="fileName",comment="保存在服务器文件名",length=100,label="文件名")
	private String fileName; //保存在服务器文件名
	
	@Column(name="fileFolder",comment="文件夹",length=100,label="路径")
	private String fileFolder; //文件路径
	
	@Column(name="fileSize",comment="文件大小",length=10,jdbcType=JdbcType.DOUBLE,label="文件大小")
	private Double fileSize; //文件大小
	
	@Column(name="uploadName",comment="上传人",length=100,label="上传者")
	private String uploadName;//上传人
	
	@Column(name="groupId",comment="上传组",length=100,label="所属组")
	private String groupId; //上传组，uuid
	
}
