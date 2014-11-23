package com.losy.common.service.impl;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.losy.common.controller.AttachmentController;
import com.losy.common.dao.IAttachmentDao;
import com.losy.common.domain.Attachment;
import com.losy.common.service.IAttachmentService;
import com.losy.common.utils.StrUtil;
import com.losy.common.service.impl.CommonServiceImpl;

@Service("attachmentService")
public class AttachmentServiceImpl extends CommonServiceImpl<IAttachmentDao, Attachment> implements IAttachmentService {

	/**
	 * 根据主键执行insert或update
	 */
	public Attachment save(Attachment vo) {
		if(vo.getId() != null) {
			return this.update(vo);
		} 
		return this.insert(vo);
	}

	@Resource(name="attachmentDao")
	public void setDao(IAttachmentDao dao) {
		this.dao = dao;
	}

	@Override
	public List<Attachment> getListByIds(String ids) {
		if(StrUtil.isNullOrEmpty(ids)) return new ArrayList<Attachment>();
		List<Serializable> idList = new ArrayList<Serializable>();
		for(String id : ids.split(",")) {
			idList.add(id);
		}
		return dao.getListByIds(idList);
	}

	
	
	@Override
	public void removeByIds(String ids) {
		List<Attachment> list = getListByIds(ids);
		super.removeByIds(ids);
		deleteFile(list);
	}

	/**
	 * 
	 */
	@Override
	public void deleteByGroupIds(String groupIds) {
		if(StrUtil.isNullOrEmpty(groupIds)) return;
		Attachment q = new Attachment();
		q.setAppendWhere(" and groupId in (" + groupIds + ")");
		List<Attachment> list = this.getList(q);
		StringBuffer sql = new StringBuffer("delete from t_common_attachment where groupId in (");
		sql.append(groupIds).append(")");
		this.executeBySql(sql.toString());
		deleteFile(list);
	}


	@Override
	public List<Attachment> getListByGroupId(String groupId) {
		if(StringUtils.isBlank(groupId)) return new ArrayList<Attachment>();
		Attachment q = new Attachment();
		q.setGroupId(groupId);
		return this.getList(q);
	}

	
	@Override
	public void deleteFile(List<Attachment> deleList) {
		File base = new File(AttachmentController.getBasePath());
		for (Attachment attachment : deleList) {
			File currentFile = new File(base,attachment.getFileLocation());
			if(currentFile.exists()) currentFile.delete();
		}
	}

	@Override
	public void deleteFile(Attachment file) {
		File base = new File(AttachmentController.getBasePath());
		File currentFile = new File(base,file.getFileLocation());
		if(currentFile.exists()) currentFile.delete();
	}

	@Override
	public List<Attachment> getListByGroupIds(Set<String> keySet) {
		if(keySet == null || keySet.size() == 0) return new ArrayList<Attachment>();
		StringBuffer condition = new StringBuffer(" and groupId in ("); 
		for (String gid : keySet) {
			condition.append("'").append(gid).append("',");
		}
		condition.setLength(condition.length() - 1);
		condition.append(")");
		Attachment q = new Attachment();
		q.setAppendWhere(condition.toString());
		return this.getList(q);
	}

	
}
