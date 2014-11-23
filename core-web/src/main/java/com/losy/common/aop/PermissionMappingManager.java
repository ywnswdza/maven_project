package com.losy.common.aop;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.losy.common.utils.SpringContextListener;
import com.losy.common.utils.StrUtil;
import com.losy.userinfo.domain.Resources;
import com.losy.userinfo.domain.Roles;
import com.losy.userinfo.domain.UserInfo;
import com.losy.userinfo.service.IResourcesService;

/**
 * 
 * @author losy
 *
 */
@Aspect
@Service("permissionMappingManager")
public class PermissionMappingManager {

	private static final Logger log = Logger.getLogger(PermissionMappingManager.class);
	
	@Autowired
	private IResourcesService rsService;
	
	//private Cache resourceCache;
	private static final Map<String,Set<String>> resMap = new HashMap<String, Set<String>>();;

	@PostConstruct
	public void permissionRefresh(){
		//new Thread(,"thread refresh permissions").start();
		ThreadPoolTaskExecutor thp = SpringContextListener.getBean("taskExecutor", ThreadPoolTaskExecutor.class);
		thp.execute(new Runnable() {
			@Override
			public void run() {
				resMap.clear();
				log.info(">>>>>>>>>>>>>>>>>>>>>>>>> Start The refresh system permissions");
				long sT = System.currentTimeMillis();
				Resources q = new Resources();
				q.setAppendWhere(" and linkurl <> ''");
				q.setIsEnabled(true);
				List<Resources> allR = rsService.getList(q);
				
				StringBuffer sqlB = new StringBuffer("SELECT srr.resourceId as id, GROUP_CONCAT(roleFlag) as roles FROM  t_sys_roles sr JOIN  t_sys_roles_resources srr");
				sqlB.append(" ON (sr.roleId = srr.roleId)").append(" WHERE srr.resourceId in (");
				
				Map<Integer,Resources> keyResourceMap = new HashMap<Integer, Resources>();
				for(Resources r : allR) { 
					sqlB.append(r.getId()).append(",");
					if(!keyResourceMap.containsKey(r.getId())) keyResourceMap.put(r.getId(), r);
				}
				sqlB.deleteCharAt(sqlB.lastIndexOf(",")).append(")");
				sqlB.append(" GROUP BY srr.resourceId");
				List<Map<String,Object>> urMp = rsService.selectListBySql(sqlB.toString());
				
				Map<Integer,Object> resourceMap = new HashMap<Integer, Object>();
				for(Map<String,Object> ur : urMp) {
					Integer id = (Integer) ur.get("id");
					resourceMap.put(id, ur.get("roles"));
				}
				
				for (Entry<Integer, Resources> entry : keyResourceMap.entrySet()) {
					Resources rs = entry.getValue();
					Set<String> roleSet = new HashSet<String>();
					String key = rs.getLinkUrl();
					if(resourceMap.containsKey(rs.getId())) {
						String roles = (String)resourceMap.get(rs.getId()) ;
						//log.info(rs.getLinkUrl() + "  " + roles);
						if(!StrUtil.isNullOrEmpty(roles)) {
							for(String role : roles.split(",")) roleSet.add(role);
						}
						if(key.indexOf("?") != -1) key = key.substring(0,key.indexOf("?"));
					}
					roleSet.add(Roles.SUPER_ROLE_FLAG);
					resMap.put(key, roleSet);
				}
				
				log.info(">>>>>>>>>>>>>>>>>>>>>>>>> refresh system permissions use time " + (System.currentTimeMillis() - sT) + "ms");
			}
		});
	}
	
	@Around("execution(* com.losy.userinfo.service.IRolesResourcesService.insertBatchByRolesId(..))" +
			"|| execution(* com.losy.userinfo.service.IRolesResourcesService.updateRRMapping(..))")
	public Object updateResourceRoleCache(ProceedingJoinPoint jp){
		Object obj = null;
		try {
			Object[] args = jp.getArgs();
			obj = jp.proceed(args);
			this.permissionRefresh();
		} catch (Throwable e) {
			log.error("PermissionMappingManager aop permissionRefresh error",e);
		}
		return obj;
	}
	
	public boolean hasPermission(String uri, UserInfo user) {
		if(user.getIsSupperUser() != null && user.getIsSupperUser()) return true;
		String targetUri = resMap.containsKey(uri) ? uri : uri.substring(1);
		if(!resMap.containsKey(targetUri)) return true;
		List<Roles> uRoles = user.getRoles();
		if(uRoles == null || uRoles.size() == 0) return false;
		Set<String> needRole = resMap.get(targetUri);
		for (Roles r : uRoles) {
			if(needRole.contains(r.getRoleFlag()))return true;
		}
		return false;
	}

	
	public void addURMapping(String uri){
		if(!resMap.containsKey(uri)) {
			Set<String> set = new HashSet<String>();
			set.add(Roles.SUPER_ROLE_FLAG);
			resMap.put(uri, set);
		}
	}
}
