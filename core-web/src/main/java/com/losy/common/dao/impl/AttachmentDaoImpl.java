package com.losy.common.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.losy.common.dao.IAttachmentDao;
import com.losy.common.domain.Attachment;
import com.losy.common.dao.impl.CommonDaoImpl;
@Repository("attachmentDao")
public class AttachmentDaoImpl extends CommonDaoImpl<Attachment> implements IAttachmentDao{

	private String nameSpace = "com.losy.sql.sqlmapping.Attachment";
	

	public String getNameSpace() {
		return nameSpace;
	}


	@Override
	public List<Attachment> getListByIds(List<Serializable> idList) {
		return getSqlSession().selectList(getNameSpace().concat(".getListByIds"),idList);
	}

}
