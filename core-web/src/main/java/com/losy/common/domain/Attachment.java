package com.losy.common.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.losy.userinfo.domain.UserInfo;

public class Attachment extends BaseVo {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3260716196718522180L;
	private Integer id;
	/** 上传时文件名 */
	private String fileRealName;
	/** 保存在服务器文件名 */
	private String fileName;
	/** 文件夹 */
	private String fileFolder;
	/** 文件大小 */
	private Long fileSize;
	/** 文件大小单位 kb or mb */
	private String sizeUnit; 
	/** 上传人 */
	private String uploadName;
	/** 上传组 */
	private String groupId;
	/** 上传时间 */
	private Date createTime;
	/**判断时间，大于或等于 */
	private Date gtCreateTime;
	/**判断时间，小于或等于 */
	private Date ltCreateTime;

	private UserInfo uploadUser = new UserInfo();
	
	public void setId(Integer id){
		this.id = id;
	}
	public Integer getId() {
		return this.id;
	}
	public void setFileRealName(String fileRealName){
		this.fileRealName = fileRealName;
	}
	public String getFileRealName() {
		return this.fileRealName;
	}
	public void setFileName(String fileName){
		this.fileName = fileName;
	}
	public String getFileName() {
		return this.fileName;
	}
	public void setFileFolder(String fileFolder){
		this.fileFolder = fileFolder;
	}
	public String getFileFolder() {
		return this.fileFolder;
	}

	public Long getFileSize() {
		return fileSize;
	}
	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}
	public void setUploadName(String uploadName){
		this.uploadName = uploadName;
	}
	public String getUploadName() {
		return this.uploadName;
	}
	public void setGroupId(String groupId){
		this.groupId = groupId;
	}
	public String getGroupId() {
		return this.groupId;
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

	
	public Date getAddTime(){
		return createTime;
	}
	
	public String getSimpleFileSize(){
		if(this.fileSize == null) return "未知";
		BigDecimal filesize  =   new  BigDecimal(this.fileSize);
        BigDecimal megabyte  =   new  BigDecimal( 1024 * 1024 );
        double  returnValue  =  filesize.divide(megabyte,  2 , BigDecimal.ROUND_UP).doubleValue();
        if  (returnValue  >   1 ) {
        	return returnValue  +   "MB";
        } else {
        	BigDecimal kilobyte  =   new  BigDecimal( 1024 );
            returnValue  =  filesize.divide(kilobyte,  2 , BigDecimal.ROUND_UP).doubleValue();
            return returnValue  +   "KB";
        }
	} 
	 
	public String getFileLocation(){
		return this.getFileFolder() + "/" + this.getFileName();
	}
	 
	public String getSimpleFileName(){
		if(this.fileRealName == null || !this.fileRealName.contains(".")) return this.fileRealName;
		return this.fileRealName.substring(0,fileRealName.lastIndexOf("."));
	}
	
	public String getType(){
		if(this.fileName == null || !this.fileName.contains(".")) return this.fileName;
		return this.fileRealName.substring(fileRealName.lastIndexOf("."));
	}
	
	public String getSizeUnit() {
		return sizeUnit;
	}
	public void setSizeUnit(String sizeUnit) {
		this.sizeUnit = sizeUnit;
	}
	public UserInfo getUploadUser() {
		return uploadUser;
	}
	public void setUploadUser(UserInfo uploadUser) {
		this.uploadUser = uploadUser;
	} 
	 
}