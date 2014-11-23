package com.losy.test;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.losy.common.AbstractBaseTest;

@ContextConfiguration(locations={"classpath:applicationContext-activiti.xml"})
public class TestMain extends AbstractBaseTest{

	@Autowired
	private RuntimeService runtimeService;
	
	@Autowired
	private TaskService taskService;
	
	@Autowired
	private RepositoryService repositoryService;
	
	@Autowired
	private HistoryService historyService;
	
	
	@Test
	public void addWorkFlow(){
		Deployment dm = repositoryService.createDeployment()
				.category("losy_test")
				.addClasspathResource("com/losy/bpmn/MyProcess.bpmn").deploy();
		//Task task = taskService.createTaskQuery().singleResult();
		ProcessDefinition pd = repositoryService.createProcessDefinitionQuery().deploymentId(dm.getId()).singleResult();
		pd.getResourceName();
		//Deployment  dl = repositoryService.createDeploymentQuery().deploymentName("losy").singleResult();
	}
	
	String businessKey = "nsdgd1";
	Map<String,Object> varable = new HashMap<String,Object>();
	
	@Test
	public void queryDeployment() throws FileNotFoundException {
		//Deployment dm = repositoryService.createDeploymentQuery().deploymentCategory("losy_test").singleResult();
		//ProcessDefinition pd = repositoryService.createProcessDefinitionQuery().deploymentId(dm.getId()).singleResult();
		//varable.put("user", "longyt");
		//runtimeService.startProcessInstanceByKey("myProcess", businessKey,varable);
		List<Task> userTasks = taskService.createTaskQuery().taskAssignee("admin").processInstanceId("80001").list();
		for(Task task : userTasks) {
			log.info(task.getAssignee() + "   " + task.getName());
			log.info(task.getProcessInstanceId());
		//	taskService.addComment(task.getId(), task.getProcessInstanceId(),"制做完了，请批阅222222222!");
			varable.put("user","admin");
			varable.put("isAgree", 1);
		//	taskService.createAttachment("doc", task.getId(), task.getProcessInstanceId(),"月表","在富商大贾 一枯要",new FileInputStream("E:\\平台管理后台V2\\联盟后台问题01.xlsx"));
		//	taskService.addComment(task.getId(), task.getProcessInstanceId(),"ok,辛苦了!");
			taskService.complete(task.getId(),varable);
		}
		
	}
	
	
	
	@Test
	public void startWorkFlow(){
		 runtimeService.startProcessInstanceByKey("apply","longyt");
	}
	
	@Test 
	public void findWorkFlowInstance(){
		//ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceBusinessKey("longyt").singleResult(); //
		List<Task> tasks = taskService.createTaskQuery().processInstanceBusinessKey(businessKey).list();
		for (Task task : tasks) {
			log.info(task.getName());
			log.info(task.getOwner());
			log.info(task);
			varable.put("approvFlag",1);
			//taskService.addComment(task.getId(), pi.getId(), "同总______________");
			//taskService.complete(task.getId(),varable);
		}
	}
	
	@Test
	public void queryHistoric(){
		/*List<HistoricTaskInstance> hisList = historyService.createHistoricTaskInstanceQuery()
				.processInstanceId("75001")
				.list();
		 for(HistoricTaskInstance hpi : hisList) {
			 log.info(hpi.getName() + " " + hpi.getAssignee());
			 List<Comment> cmts = taskService.getTaskComments(hpi.getId());
			 for(Comment ct : cmts)
				 log.info(ct.getFullMessage());
		 }*/
		 List<HistoricActivityInstance> actInstList = historyService.createHistoricActivityInstanceQuery()
				 .processInstanceId("75001").list();
		for(HistoricActivityInstance hai : actInstList) {
			log.info(hai.getActivityName() + " " + hai.getAssignee());
		}
	}
	
	@Test
	public void deleteDeployment(){
		List<Deployment> ds = repositoryService.createDeploymentQuery().list();
		for(Deployment d : ds) {
			repositoryService.deleteDeployment(d.getId());
		}
	}
	
	@Test
	public void identityTest(){
		
	}
	
}
