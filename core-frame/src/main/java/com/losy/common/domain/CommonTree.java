package com.losy.common.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
/**
 * @entity
 * @table t_common_tree
 * @date 2014-05-05 15:18:45
 * @author losy
 */
@JsonSerialize(include=Inclusion.ALWAYS)
public class CommonTree extends BaseVo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8689062983517776819L;
	/** id */
	private Integer id;
	/** 父节点 */
	private Integer pId;
	/** 层级 */
	private Integer depth;
	/** 节点名称 */
	private String nodeText;
	/** 种类 */
	private Integer kindId;
	/** 是否有效 */
	private Boolean isValid;
	/** 是否子节点 */
	private Boolean isLeaf;
	/** 描述 */
	private String desc;
	
	/** 优先级 */
	private Integer priority;
	
	/** 是否可以拖动 **/
	private Boolean isDrap = true;
	
	/** 是否可以拖动到本身 **/
	private Boolean isDrop = true;
	
	/** 是否可以展开 **/
	private Boolean isOpen = false;
	
	/** 唯一标示 **/
	private String flag;

	/** 更新时间 */
	private Date updateTime;
	/**判断更新时间，大于或等于 */
	private Date	gtUpdateTime;
	/**判断更新时间，小于或等于 */
	private Date	ltUpdateTime;
	/** 创建时间 */
	private Date createTime;
	/**判断创建时间，大于或等于 */
	private Date	gtCreateTime;
	/**判断创建时间，小于或等于 */
	private Date	ltCreateTime;

	
	private List<CommonTree> children;
	
	public void setId(Integer id){
		this.id = id;
	}
	public Integer getId() {
		return this.id;
	}
	public void setPId(Integer pId){
		this.pId = pId;
	}
	public Integer getPId() {
		return this.pId;
	}
	public void setDepth(Integer depth){
		this.depth = depth;
	}
	public Integer getDepth() {
		return this.depth;
	}
	public void setNodeText(String nodeText){
		this.nodeText = nodeText;
	}
	public String getNodeText() {
		return this.nodeText;
	}
	public void setKindId(Integer kindId){
		this.kindId = kindId;
	}
	public Integer getKindId() {
		return this.kindId;
	}
	public void setIsValid(Boolean isValid){
		this.isValid = isValid;
	}
	public Boolean getIsValid() {
		return this.isValid;
	}
	public void setIsLeaf(Boolean isLeaf){
		this.isLeaf = isLeaf;
	}
	public Boolean getIsLeaf() {
		return this.isLeaf;
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
	public Date getGtUpdateTime() {
		return this.gtUpdateTime;
	}
	public void setLtUpdateTime(Date ltUpdateTime){
		this.ltUpdateTime = ltUpdateTime;
	}
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
	public Date getGtCreateTime() {
		return this.gtCreateTime;
	}
	public void setLtCreateTime(Date ltCreateTime){
		this.ltCreateTime = ltCreateTime;
	}
	public Date getLtCreateTime() {
		return this.ltCreateTime;
	}

	public Boolean getIsParent(){
		if(this.isLeaf == null) return false;
		return !this.isLeaf;
	}
	public Integer getpId() {
		return pId;
	}
	public void setpId(Integer pId) {
		this.pId = pId;
	}
	public Boolean getIsDrap() {
		return isDrap;
	}
	public void setIsDrap(Boolean isDrap) {
		this.isDrap = isDrap;
	}
	public Boolean getIsDrop() {
		return isDrop;
	}
	public void setIsDrop(Boolean isDrop) {
		this.isDrop = isDrop;
	}
	public Boolean getIsOpen() {
		return isOpen;
	}
	public void setIsOpen(Boolean isOpen) {
		this.isOpen = isOpen;
	}
	public Integer getPriority() {
		return priority;
	}
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	
	@Override
	public String toString() {
		return "CommonTree [id=" + id + ", pId=" + pId + ", depth=" + depth
				+ ", nodeText=" + nodeText + ", kindId=" + kindId
				+ ", isValid=" + isValid + ", isLeaf=" + isLeaf + ", desc="
				+ desc + ", isDrap=" + isDrap + ", isDrop=" + isDrop
				+ ", isOpen=" + isOpen + ", priority=" + priority
				+ ", updateTime=" + updateTime + ", gtUpdateTime="
				+ gtUpdateTime + ", ltUpdateTime=" + ltUpdateTime
				+ ", createTime=" + createTime + ", gtCreateTime="
				+ gtCreateTime + ", ltCreateTime=" + ltCreateTime + "]";
	}
	
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}

	
	public CommonTree addChildren(CommonTree c){
		if(this.children == null) this.children = new ArrayList<CommonTree>();
		if(!this.children.contains(c)) this.children.add(c);
		return this;
	}
	
	public List<CommonTree> findChildren(){
		if(this.children == null) return new ArrayList<CommonTree>();
		return this.children;
	}
}