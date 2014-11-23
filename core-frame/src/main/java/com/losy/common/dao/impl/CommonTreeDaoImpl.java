package com.losy.common.dao.impl;

import org.springframework.stereotype.Repository;

import com.losy.common.dao.ICommonTreeDao;
import com.losy.common.dao.impl.CommonDaoImpl;
import com.losy.common.domain.CommonTree;

/**
 * @daoimpl
 * @table t_common_tree
 * @date 2014-05-05 15:18:45
 * @author losy
 */
@Repository("commonTreeDao")
public class CommonTreeDaoImpl extends CommonDaoImpl<CommonTree> implements ICommonTreeDao{

	private String nameSpace = "com.losy.sql.sqlmapping.CommonTree";
	

	public String getNameSpace() {
		return nameSpace;
	}

}
