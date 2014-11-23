package com.losy.common.utils;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.apache.commons.lang.StringUtils;

import com.losy.common.domain.CommonTree;
import com.losy.common.service.ICommonTreeService;
import com.losy.common.utils.SpringContextListener;
/**
 * @date 2014-05-13
 * @author losy
 *
 */
public class DataDictionarySupport extends SimpleTagSupport {

	private Integer kindId;
	
	private Integer depth;
	
	private Integer pId;
	
	private String style;
	
	private String name;
	
	private String id;
	
	private String defaultValue;
	
	private String className;
	
	private String ltype;
	
	private String hideValue;//添加不需要显示的value，以“，”分隔，如“value1，value2，value3”
	
	private String initId;
	
	private String initValue;
	
	private String setOptValueFlag;
	
	
	
	@Override
	public void doTag() throws JspException, IOException {
		if(kindId == null && pId == null && depth == null) return;
		ICommonTreeService treeService = SpringContextListener.getBean("commonTreeService", ICommonTreeService.class);
		String[] hideValues = null;
		CommonTree queryVo = new CommonTree();
		queryVo.setKindId(kindId);
		queryVo.setDepth(depth);
		queryVo.setpId(pId);
		queryVo.setOrderBy("priority");
		queryVo.setDescOrAsc("asc");
		List<CommonTree> list = treeService.getList(queryVo);
		StringBuffer html = new StringBuffer("<select");
		if(StringUtils.isNotBlank(hideValue)){hideValues = hideValue.split(",");}
		if(StringUtils.isNotBlank(id))html.append(" id=\"").append(id).append("\" ");
		html.append(" name=\"").append(name).append("\"");
		if(StringUtils.isNotBlank(style))html.append(" style=\"").append(style).append("\" ");
		if(StringUtils.isNotBlank(ltype))html.append(" ltype=\"").append(ltype).append("\" ");
		if(StringUtils.isNotBlank(className))html.append(" class=\"").append(className).append("\" ");
		html.append(">");
	
		initValueAndId(html);
		
		for (CommonTree commonTree : list) {
			boolean flag = true;
			if(hideValues!=null){
				for(int i=0;i<hideValues.length;i++){
					if(commonTree.getId().intValue()==(Integer.valueOf(hideValues[i])).intValue()){
						flag = false;
					}
				}
			}	
			if(flag){
				if(setOptValueFlag!=null&&!setOptValueFlag.equals("")){
					html.append("<option value=\"").append(commonTree.getFlag()).append("\"");
				}else{
					html.append("<option value=\"").append(commonTree.getId()).append("\"");
				}
				if(String.valueOf(commonTree.getId()).equals(defaultValue) || commonTree.getNodeText().equals(defaultValue)) {
					html.append(" selected=\"selected\"");
				}
				html.append(">").append(commonTree.getNodeText()).append("</option>");
			}else{
				continue;
			}
		}
		html.append("</select>");
		this.getJspContext().getOut().write(html.toString());
		super.doTag();
	}

	
	private void initValueAndId(StringBuffer html) {
		if(this.initId == null || this.initValue == null) return;
		String[] ids = initId.split(",");
		String[] value = initValue.split(",");
		if(ids.length != value.length) return;
		for (int i = 0;i < ids.length;i++) {
			html.append("<option value=\"").append(ids[i]).append("\">").append(value[i]).append("</option>");
		}
	}


	public Integer getKindId() {
		return kindId;
	}

	public void setKindId(Integer kindId) {
		this.kindId = kindId;
	}

	public Integer getDepth() {
		return depth;
	}

	public void setDepth(Integer depth) {
		this.depth = depth;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getDefaultValue() {
		return defaultValue;
	}


	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public Integer getpId() {
		return pId;
	}

	public void setpId(Integer pId) {
		this.pId = pId;
	}



	public String getClassName() {
		return className;
	}



	public void setClassName(String className) {
		this.className = className;
	}


	public String getHideValue() {
		return hideValue;
	}


	public void setHideValue(String hideValue) {
		this.hideValue = hideValue;
	}


	public void setLtype(String ltype) {
		this.ltype = ltype;
	}


	public String getInitId() {
		return initId;
	}


	public void setInitId(String initId) {
		this.initId = initId;
	}


	public String getInitValue() {
		return initValue;
	}


	public void setInitValue(String initValue) {
		this.initValue = initValue;
	}

	public String getSetOptValueFlag() {
		return setOptValueFlag;
	}


	public void setSetOptValueFlag(String setOptValueFlag) {
		this.setOptValueFlag = setOptValueFlag;
	}

	
}
