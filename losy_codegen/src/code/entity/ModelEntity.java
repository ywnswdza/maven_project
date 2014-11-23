package code.entity;

import java.util.Date;

import com.losy.codegen.annotation.Column;
import com.losy.codegen.annotation.JdbcType;
import com.losy.codegen.annotation.Table;

@Table
public class ModelEntity {
	
	  @Column(id=true)
	  protected String id;
	  @Column(label="最新版本")
	  protected int revision = 1;
	  @Column(label="名称")
	  protected String name;
	  @Column(label="key值")
	  protected String key;
	  @Column(label="类别")
	  protected String category;
	  @Column(label="创建时间",jdbcType=JdbcType.DATE)
	  protected Date createTime;
	  @Column(label="更新时间",jdbcType=JdbcType.DATE)
	  protected Date lastUpdateTime;
	  @Column(label="版本")
	  protected Integer version = 1;
	  @Column(label="头信息")
	  protected String metaInfo;
	  @Column(label="发布Id")
	  protected String deploymentId;
	  protected String editorSourceValueId;
	  protected String editorSourceExtraValueId;
	  protected String tenantId;
}
