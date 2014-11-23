package com.losy.common.dao;

import java.io.Serializable;
import java.util.List;

import com.losy.common.domain.Attachment;
import com.losy.common.dao.ICommonDao;

public interface IAttachmentDao extends ICommonDao<Attachment> {

	List<Attachment> getListByIds(List<Serializable> idList);

}