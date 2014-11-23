package com.sys.workflow.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.identity.Group;
import org.activiti.engine.impl.ProcessEngineImpl;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.bpmn.diagram.ProcessDiagramGenerator;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;

import com.sys.annotation.Bpmn;
import com.sys.annotation.Bpmn.BpmnType;
import com.sys.entity.bean.DataParam;
import com.sys.entity.bean.EntryKV;
import com.sys.utils.BaseUtils;
import com.sys.utils.EntityUtils;

/**
 * 工作流工具类:历史任务,流程图操作
 * @author evan
 *
 */
public class WorkFlowHandleUtils {
	
	public static final String WORK_FLOW_KEY = "wf_key";
	
	// 根据用户获取所属角色
	public static List<String> findRoleByUser() throws Exception {
		List<String> list = new ArrayList<String>();
		
		List<Group> gList = WorkFlowBaseUtils.getIdentityService().createGroupQuery()
							.groupMember(BaseUtils.getLoginUserName())
							.list();
		for(Group group : gList) {
			list.add(group.getName());
		}
		return list;
	}
	
	// 查询审批页面总记录数和当前页businessKey
	public static Integer findCountAndIds(DataParam dataParam) throws Exception{
		
		Class<?> clazz = (Class<?>) dataParam.get(WorkFlowHandleUtils.WORK_FLOW_KEY);
		
		if(clazz == null) {
			throw new RuntimeException("must be set work_flow_key for search approval data");
		}
		
		Bpmn bpmn = clazz.getAnnotation(Bpmn.class);
		if(bpmn == null || StringUtils.isBlank(bpmn.key())) {
			throw new RuntimeException("param Bpmn setting error");
		}
		
		Integer rows = (Integer) dataParam.get("rows");
		Integer page = (Integer) dataParam.get("page");
		
		WorkFlowBaseUtils.getIdentityService().createUserQuery()
						.memberOfGroup("用户")
						.userId("user")
						.list();
		
		List<String> gList = findRoleByUser();
		
		// 个人任务
		Long count = WorkFlowBaseUtils.getTaskService().createTaskQuery()
				.taskAssignee(BaseUtils.getLoginUserName())
				.processDefinitionKey(bpmn.key())
				.count();
		
		// 组任务
		Long groupCount = WorkFlowBaseUtils.getTaskService().createTaskQuery()
							.taskCandidateGroupIn(gList)
							.processDefinitionKey(bpmn.key())
							.count();
		// 总任务
		List<Task> list = new ArrayList<Task>();
		
		// 个人任务
		List<Task> taskList = WorkFlowBaseUtils.getTaskService().createTaskQuery()
				.taskAssignee(BaseUtils.getLoginUserName())
				.processDefinitionKey(bpmn.key())
				.listPage((page - 1) * rows, rows);
		list.addAll(taskList);
		
		if(list.size() < rows) { 
			
			Integer groupStartIndex = 0;
			
			if(count.intValue() / rows == (page - 1)) { // 个人任务取了一部分,余下从组任务中获取
				groupStartIndex = 0;
			}else { // 全部从组任务中获取
				groupStartIndex = (page - 1) * rows - count.intValue();
			}
			
			List<Task> groupList = WorkFlowBaseUtils.getTaskService().createTaskQuery()
												.taskCandidateGroupIn(gList)
												.processDefinitionKey(bpmn.key())
												.listPage(groupStartIndex, rows - list.size());
			list.addAll(groupList);
		}
		
		List<String> ids = new ArrayList<String>();
		
		for(Task task : list) {
			String piId = task.getProcessInstanceId();
			ProcessInstance pi = WorkFlowBaseUtils.getRuntimeService().createProcessInstanceQuery()
						.processInstanceId(piId)
						.singleResult();
			ids.add(pi.getBusinessKey());
		}
		dataParam.put("ids", ids);
		return count.intValue() + groupCount.intValue();
	}
	
	
	// 查看高亮流程节点
	public static InputStream highLineWorkFlowPic(String businessKey) {
		
		ProcessInstance processInstance = WorkFlowBaseUtils.getRuntimeService().createProcessInstanceQuery()
												.processInstanceBusinessKey(businessKey)
												.singleResult();
		 
        BpmnModel bpmnModel = WorkFlowBaseUtils.getRepositoryService()
        							.getBpmnModel(processInstance.getProcessDefinitionId());  
       
        List<String> activeActivityIds =  WorkFlowBaseUtils.getRuntimeService()
        									.getActiveActivityIds(processInstance.getId());  
        ProcessEngineImpl defaultProcessEngine = (ProcessEngineImpl) WorkFlowBaseUtils.getProcessEngine();  
        Context.setProcessEngineConfiguration(defaultProcessEngine.getProcessEngineConfiguration());  
        InputStream imageStream = ProcessDiagramGenerator.generateDiagram(bpmnModel, "png", activeActivityIds);  
		return imageStream;
	}
	
	// 获取流程图所有节点
	public static List<EntryKV> findWorkFlowAllElement(String key) {
		ProcessDefinition pi = WorkFlowBaseUtils.getRepositoryService().createProcessDefinitionQuery()
												.processDefinitionKey(key)
												.latestVersion()
												.singleResult();
		ProcessDefinitionEntity def = (ProcessDefinitionEntity) ((RepositoryServiceImpl)WorkFlowBaseUtils.getRepositoryService())
												.getDeployedProcessDefinition(pi.getId());
		List<ActivityImpl> activitiList = def.getActivities();
		List<EntryKV> entryList = new ArrayList<EntryKV>();
		for(ActivityImpl ai : activitiList) {
			EntryKV kv = new EntryKV();
			kv.setKey(ai.getId());
			kv.setValue((String)ai.getProperty("name"));
			if(((String)ai.getProperty("type")).endsWith("Task")) {
				entryList.add(kv);
			}
		}
		return entryList;
	}
	
	// 获取流程审批历史
	public static List<WorkFlowHistoryInfo> findApprovHistory(String key) {
		List<HistoricTaskInstance> hisList = WorkFlowBaseUtils.getHistoryService().createHistoricTaskInstanceQuery()
							.processInstanceBusinessKey(key)
							.orderByHistoricTaskInstanceEndTime().desc()
							.list();
		List<WorkFlowHistoryInfo> list = new ArrayList<WorkFlowHistoryInfo>();
		List<HistoricVariableInstance> hisVarList = null;
		
		for(HistoricTaskInstance his : hisList) {
			if(his.getEndTime() == null) { // 表示该任务还没有被处理
				continue;
			}
			WorkFlowHistoryInfo info = new WorkFlowHistoryInfo();
			info.setApprovUser(his.getAssignee());
			info.setApprovDate(his.getEndTime());
			
			List<Comment> conmentList = WorkFlowBaseUtils.getTaskService().getTaskComments(his.getId());
			
			if(conmentList.size() > 0) {
				String message = conmentList.get(0).getFullMessage();
				if(message.contains("_")){
					info.setApprovFlag(message.split("_")[0]);
					info.setApprovDesc(message.split("_")[1]);
				}
			}
			
			list.add(info);
		}
		return list;
	}
	
	// 获取流程图片
	public static InputStream workFlowPic(String key) throws Exception {
		RepositoryService repositoryService = WorkFlowBaseUtils.getRepositoryService();
		
		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()  
										        .processDefinitionKey(key)
										        .latestVersion()
										        .singleResult();  
		BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinition.getId()); 
		Context.setProcessEngineConfiguration(((ProcessEngineImpl)WorkFlowBaseUtils.getProcessEngine()).getProcessEngineConfiguration());  
        // 得到图片输出流
        InputStream imageStream = ProcessDiagramGenerator.generatePngDiagram(bpmnModel);
		
		return imageStream;
	}
	
	// 删除流程
	public static void deleteDeployment(String key) {
		
		List<Deployment> list = WorkFlowBaseUtils.getRepositoryService()
											.createDeploymentQuery()
											.processDefinitionKey(key)
											.list();
		for(Deployment d : list) {
			WorkFlowBaseUtils.getRepositoryService().deleteDeployment(d.getId());
		}
	}
	
	// 部署流程
	public static ProcessDefinition deployWorkFlow(Object obj) throws Exception {
		
		File file = null;
		String fileName = null;
		
		for(Field field : obj.getClass().getDeclaredFields()) {
			if(file != null && fileName != null) {
				break;
			}
			Bpmn bpmn = field.getAnnotation(Bpmn.class);
			if(bpmn != null) {
				if(bpmn.name() == BpmnType.FILE) {
					file = (File) EntityUtils.getValueByFieldName(obj, field.getName());
				}else if(bpmn.name() == BpmnType.FLIE_NAME) {
					fileName = (String) EntityUtils.getValueByFieldName(obj, field.getName());
				}
			}
		}
		if(file == null) {
			throw new RuntimeException("can't find the bmpm file!");
		}
		if(fileName == null) {
			fileName = file.getName() == null ? "null.bpmn" : file.getName();
		}
		
    	// 部署流程
		Deployment deployment = WorkFlowBaseUtils.getRepositoryService()
										.createDeployment()
										.name(obj.getClass().getSimpleName())
										// .addBpmnModel(picName, bpmnModel)
										.addInputStream(fileName, new FileInputStream(file))
										.deploy();
		
		// 根据流程ID获取流程定义
		ProcessDefinition pd = WorkFlowBaseUtils.getRepositoryService()
												.createProcessDefinitionQuery()
												.deploymentId(deployment.getId())
												.singleResult();
		return pd;
	}
}
