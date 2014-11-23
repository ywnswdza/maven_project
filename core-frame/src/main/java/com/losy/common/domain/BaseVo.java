package com.losy.common.domain;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;


@JsonSerialize(include=Inclusion.NON_DEFAULT)
public abstract class BaseVo implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 6542701380619247792L;
	
	protected static final Logger log = Logger.getLogger(BaseVo.class);

	private String orderBy;
	
	private String descOrAsc;
	
	private int refreshType;
	
	private String appendWhere;
	
	@JsonIgnore
	public int getRefreshType() {
		return refreshType;
	}

	public void setRefreshType(int refreshType) {
		this.refreshType = refreshType;
	}
	@JsonIgnore
	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
	@JsonIgnore
	public String getDescOrAsc() {
		return descOrAsc;
	}

	public void setDescOrAsc(String descOrAsc) {
		this.descOrAsc = descOrAsc;
	}

	@JsonIgnore
	public String getAppendWhere() {
		return appendWhere;
	}

	public void setAppendWhere(String appendWhere) {
		this.appendWhere = appendWhere;
	}

	
	public String orderSql(String defaultOrder){
		String orderBy = " order by ";
		if(StringUtils.isBlank(this.getOrderBy()))
			orderBy += defaultOrder;
		else 
			orderBy += this.getOrderBy();
		if(StringUtils.isNotBlank(this.getDescOrAsc()))
			orderBy += " " + this.getDescOrAsc();
		else
			orderBy += " desc ";
		return orderBy;
	}
}
