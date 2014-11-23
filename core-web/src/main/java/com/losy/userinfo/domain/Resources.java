package com.losy.userinfo.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.losy.common.domain.BaseVo;
@JsonSerialize(include=Inclusion.ALWAYS)
public class Resources extends BaseVo {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2222974050049075093L;
	/** id */
	private Integer id;
	/** 资源名称 */
	private String name;
	/** 1:顶级菜单,2:url,3:method */
	private Integer type;
	/** 资源链接 */
	private String linkUrl;
	/** 优先级 */
	private Integer priority;
	/** 是否有效 */
	private Boolean isEnabled;
	/** 父级目录 */
	private Integer parentId;
	/** 描述 */
	private String desc;
	
	/** 深度 **/
	private Integer depth;
	
	/** 否是叶子节点 **/
	private Boolean isLeaf;
		
	
	/** 每次点击菜单时是否刷新页面  **/
	private Boolean isRefresh;
	
	/** 创建时间 */
	private Date updateTime;
	/**判断时间，大于或等于 */
	private Date	gtUpdateTime;
	/**判断时间，小于或等于 */
	private Date	ltUpdateTime;
	/** 更新时间 */
	private Date createTime;
	/**判断时间，大于或等于 */
	private Date	gtCreateTime;
	/**判断时间，小于或等于 */
	private Date	ltCreateTime;
	
	private List<Resources> children;
	
	public Resources() {
	}
	
	
	public Resources(Integer id,String name) {
		this.id = id;
		this.name = name;
	}
	
	
	public void setId(Integer id){
		this.id = id;
	}
	public Integer getId() {
		return this.id;
	}
	public void setName(String name){
		this.name = name;
	}
	public String getName() {
		return this.name;
	}
	public void setType(Integer type){
		this.type = type;
	}
	public Integer getType() {
		return this.type;
	}
	public void setLinkUrl(String linkUrl){
		this.linkUrl = linkUrl;
	}
	public String getLinkUrl() {
		return this.linkUrl;
	}
	public void setPriority(Integer priority){
		this.priority = priority;
	}
	public Integer getPriority() {
		return this.priority;
	}
	public void setIsEnabled(Boolean isEnabled){
		this.isEnabled = isEnabled;
	}
	public Boolean getIsEnabled() {
		return this.isEnabled;
	}
	public void setParentId(Integer parentId){
		this.parentId = parentId;
	}
	public Integer getParentId() {
		return this.parentId;
	}
	public void setDesc(String desc){
		this.desc = desc;
	}
	public String getDesc() {
		return this.desc;
	}
	public void setUpdateTime(Date updateTime){
		this.updateTime = updateTime;
	}
	public Date getUpdateTime() {
		return this.updateTime;
	}
	public void setGtUpdateTime(Date gtUpdateTime){
		this.gtUpdateTime = gtUpdateTime;
	}
	@JsonIgnore
	public Date getGtUpdateTime() {
		return this.gtUpdateTime;
	}
	public void setLtUpdateTime(Date ltUpdateTime){
		this.ltUpdateTime = ltUpdateTime;
	}
	@JsonIgnore
	public Date getLtUpdateTime() {
		return this.ltUpdateTime;
	}
	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}
	public Date getCreateTime() {
		return this.createTime;
	}
	public void setGtCreateTime(Date gtCreateTime){
		this.gtCreateTime = gtCreateTime;
	}
	@JsonIgnore
	public Date getGtCreateTime() {
		return this.gtCreateTime;
	}
	public void setLtCreateTime(Date ltCreateTime){
		this.ltCreateTime = ltCreateTime;
	}
	@JsonIgnore
	public Date getLtCreateTime() {
		return this.ltCreateTime;
	}
	public Integer getDepth() {
		return depth;
	}
	public void setDepth(Integer depth) {
		this.depth = depth;
	}
	public Boolean getIsLeaf() {
		return isLeaf;
	}
	public void setIsLeaf(Boolean isLeaf) {
		this.isLeaf = isLeaf;
	}
	
	public Boolean getIsParent(){
		if(this.isLeaf == null) return true;
		return !this.isLeaf;
	}
	
	
	@JsonIgnore
	public List<Resources> getChildren() {
		return children;
	}
	public void setChildren(List<Resources> children) {
		this.children = children;
	}
	public Resources addChild(Resources c){
		if(this.children == null) this.children = new ArrayList<Resources>();
		this.children.add(c);
		return this;
	}
	
	
	public Boolean getIsRefresh() {
		return isRefresh;
	}

	public void setIsRefresh(Boolean isRefresh) {
		this.isRefresh = isRefresh;
	}

	public static Resources builderResource(String name,String linkUrl,Integer paretnId,Integer priority,Integer depth,Integer type) {
		Resources r = new Resources();
		r.setParentId(paretnId);
		r.setCreateTime(new Date());
		r.setDepth(depth);
		r.setIsEnabled(true);
		r.setIsLeaf(true);
		r.setName(name);
		r.setType(type);
		r.setLinkUrl(linkUrl);
		r.setPriority(priority);
		return r ;
	}
	
	
	public String getIconSkin() {
		return "type" + this.type;
	}
}