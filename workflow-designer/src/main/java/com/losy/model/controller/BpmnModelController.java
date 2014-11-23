package com.losy.model.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.impl.persistence.entity.ModelEntity;
import org.activiti.engine.impl.util.json.JSONObject;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ModelQuery;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.losy.common.utils.MD5Util;
import com.losy.common.utils.Page;

@Controller
@RequestMapping("/model")
public class BpmnModelController {

	@Autowired
	private RepositoryService repositoryService;

	@RequestMapping(value = "/listData")
	@ResponseBody
	public Object modelList(Page<Model> page, ModelEntity condition) {
		ModelQuery mq = repositoryService.createModelQuery();
		if(StringUtils.isNotBlank(condition.getName())) mq.modelNameLike("%" + condition.getName() + "%");
		
		List<Model> lists =  mq.listPage((page.getPageNo() - 1) * page.getPageSize(),page.getPageSize());
		page = new Page<Model>(lists, new Long(mq.count()).intValue(), page.getPageNo(), page.getPageSize());
		return page;
	}

	/** 添加或修改模型 */
	@RequestMapping(value = "/edit")
	public String addOrUpdateModel(String modelId,
			org.springframework.ui.Model model) {
		if (StringUtils.isNotBlank(modelId)) {
			Model m = repositoryService.createModelQuery().modelId(modelId)
					.singleResult();
			model.addAttribute("vo", m);
			if (m != null && StringUtils.isNotBlank(m.getMetaInfo())) {
				JSONObject json = new JSONObject(m.getMetaInfo());
				model.addAttribute("description", json.get("description"));
			}
		}
		return "/model/modelEntityEdit";
	}

	/**
	 * 何存模型
	 * 
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/save")
	@ResponseBody
	public Object save(ModelEntity vo, String description,HttpServletResponse response)
			throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		ObjectNode modelObjectNode = objectMapper.createObjectNode();
		modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, vo.getName());
		modelObjectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, description);		
		modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, 1);
		vo.setMetaInfo(modelObjectNode.toString());
		ObjectNode editorNode = objectMapper.createObjectNode();
		String sourceId = MD5Util.calc(vo.getKey());
		editorNode.put("id",sourceId);
		editorNode.put("resourceId",sourceId);
		ObjectNode stencilSetNode = objectMapper.createObjectNode();
		stencilSetNode.put("namespace","http://b3mn.org/stencilset/bpmn2.0#");
		editorNode.put("stencilset", stencilSetNode);
		repositoryService.saveModel(vo);
		repositoryService.addModelEditorSource(vo.getId(), editorNode.toString().getBytes("utf-8"));
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("id", vo.getId());
		return map;
	}

	/**
	 * 发布流程
	 * 
	 * @param modelId
	 * @return
	 * @throws IOException 
	 * @throws JsonProcessingException 
	 */
	@RequestMapping("/deployment")
	@ResponseBody
	public Object deploymentModel(String modelId) throws JsonProcessingException, IOException {
		Model modelData = repositoryService.getModel(modelId);
		BpmnJsonConverter jsonConverter = new BpmnJsonConverter();
		byte[] source = repositoryService.getModelEditorSource(modelData.getId());
		JsonNode editorNode = new ObjectMapper().readTree(new ByteArrayInputStream(source,0,source.length));
		BpmnModel bpmnModel = jsonConverter.convertToBpmnModel(editorNode);
		BpmnXMLConverter xmlConverter = new BpmnXMLConverter();
		byte[] bpmnBytes = xmlConverter.convertToXML(bpmnModel);
		String processName = modelData.getName() + ".bpmn20.xml";
		Deployment dy = repositoryService.createDeployment().category(modelData.getCategory()).name(modelData.getName())
		.addString(processName,new String(bpmnBytes,"UTF-8")).deploy();
		return dy.getId();
	}

	/**
	 * 导出 xml模型
	 * 
	 * @param modelId
	 * @throws IOException 
	 * @throws JsonProcessingException 
	 */
	@RequestMapping("/export") 
	public ResponseEntity<byte[]> modelToXml(String modelId) throws JsonProcessingException, IOException {
		ResponseEntity<byte[]> result = null;
		Model modelData = repositoryService.getModel(modelId);
		BpmnJsonConverter jsonConverter = new BpmnJsonConverter();
		byte[] source = repositoryService.getModelEditorSource(modelData.getId());
		JsonNode editorNode = new ObjectMapper().readTree(new ByteArrayInputStream(source,0,source.length));
		BpmnModel bpmnModel = jsonConverter.convertToBpmnModel(editorNode);
		BpmnXMLConverter xmlConverter = new BpmnXMLConverter();
		byte[] bpmnBytes = xmlConverter.convertToXML(bpmnModel);
		String processName = modelData.getName() + ".bpmn20.xml";
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.setContentDispositionFormData("attachment",new String(processName.getBytes(),"ISO-8859-1"));
		responseHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);  
		result = new ResponseEntity<byte[]>(bpmnBytes,responseHeaders, HttpStatus.CREATED);
		return result;
	}
}
