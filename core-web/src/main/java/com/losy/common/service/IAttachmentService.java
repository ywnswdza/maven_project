package com.losy.common.service;

import java.util.List;
import java.util.Set;

import com.losy.common.domain.Attachment;
import com.losy.common.service.ICommonService;

public interface IAttachmentService extends ICommonService<Attachment> {

	List<Attachment> getListByIds(String ids);

	/**
	 * 根据groupId 删除
	 * @param groupIds,多个以逗号隔开
	 */
	public void deleteByGroupIds(String groupIds);

	
	List<Attachment> getListByGroupId(String groupId);

	void deleteFile(List<Attachment> deleList);
	void deleteFile(Attachment file);

	List<Attachment> getListByGroupIds(Set<String> keySet);
}
