package com.losy.common.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.StringUtils;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.losy.common.domain.Attachment;
import com.losy.common.service.IAttachmentService;
import com.losy.common.utils.StrUtil;
import com.losy.common.utils.SpringContextListener;

@Controller("attachmentController")
@RequestMapping(value="/system/attachment")
public class AttachmentController extends CommonController<IAttachmentService,Attachment>  {

	private static final String folder_name = "/attachment";
	public static final String UPLOAD_BASE_PATH = SpringContextListener.getContextProValue("uploadBasePath","");
	
	@Resource(name="attachmentService")
	public void setService(IAttachmentService service) {
		this.service = service;
	}

	
	public static String getBasePath() {
		return !StrUtil.isNullOrEmpty(UPLOAD_BASE_PATH) ? UPLOAD_BASE_PATH : ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest().getServletContext().getRealPath("/uf/");
	}
	
	
	/**
	 * 多文件上传
	 * @param request
	 * @param response
	 * @param attachPojo
	 * @param result
	 * @throws IOException
	 */
	@RequestMapping(value="/uploads")
	public void uploads(HttpServletRequest request,HttpServletResponse response, Attachment attachPojo,Map<String,Object> result) throws IOException{
		StringBuffer basePath = new StringBuffer(getBasePath());
		JSONObject json = new JSONObject();
		// 设置上下方文  
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());  
        // 检查form是否有enctype="multipart/form-data"  
        if(StrUtil.isNullOrEmpty(attachPojo.getGroupId())) attachPojo.setGroupId(UUID.randomUUID().toString());
        Attachment back  = attachPojo;
        JSONArray ids = new JSONArray();
        JSONArray files = new JSONArray();

        if (multipartResolver.isMultipart(getRequest())) {  
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;  
            Iterator<String> iter = multiRequest.getFileNames();  
            while (iter.hasNext()) {  
                // 由CommonsMultipartFile继承而来,拥有上面的方法.  
                MultipartFile attach = multiRequest.getFile(iter.next());  
                if (attach != null && StringUtils.isNotEmpty(attach.getOriginalFilename())) {   
                	try {
	                	String folder = attachPojo.getFileFolder();
	    				String filename = attach.getOriginalFilename();
	    				if(!StrUtil.isNullOrEmpty(folder)) {
	    					if(folder.startsWith("/")) folder = folder.substring(folder.indexOf("/") + 1);
	    					if(folder.endsWith("/")) folder =  folder.substring(0,folder.lastIndexOf("/"));
	    					folder += "/" + new SimpleDateFormat("yyyyMM").format(new Date());
	    					attachPojo.setFileFolder(folder);
	    				} else {
	    					folder = new SimpleDateFormat("yyyyMM").format(new Date());
	    					attachPojo.setFileFolder(folder);
	    				}
	    				String fileext = filename.substring(filename.lastIndexOf("."));
	    				attachPojo.setFileName(UUID.randomUUID() + fileext);
	    				attachPojo.setFileRealName(attach.getOriginalFilename());//原始文件名
	    				attachPojo.setFileSize(attach.getSize());
	    				attachPojo.setCreateTime(new Date());
	    				//attachPojo.setUploadName("龙运涛");
	    				service.save(attachPojo);
	    				
	    				ids.add(attachPojo.getId().toString());
	    				files.add(attachPojo.getFileLocation());
	    				
	    				File baseFile = new File(basePath.toString(),folder);
	    				if(!baseFile.exists()) baseFile.mkdirs();
						FileCopyUtils.copy(attach.getBytes(), new File(baseFile,attachPojo.getFileName()));
						attachPojo = null;
						attachPojo = back;
					} catch (IOException e) {
						log.error(e.getMessage(),e);
					}
                }  
            }  
        }  
        json.put("ids",ids.toString());
        json.put("filesPath",files.toString());
        json.put("groupId",attachPojo.getGroupId());
        response.reset();
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
		response.getWriter().print(json.toString());
        return;
	}
	
	@RequestMapping(value="/download")
	public void download(HttpServletResponse response,Integer id) throws IOException{
		//StringBuffer basePath = new StringBuffer(!StrUtil.isNullOrEmpty(UPLOAD_BASE_PATH) ? UPLOAD_BASE_PATH : getRequest().getServletContext().getRealPath("/uf")); 
		response.reset();
        // 设置response的Header
		Attachment attachment = service.getObjectById(id);
		dowloadOne(response, attachment);
        return;
	}
	
	/**
	 * 下载一组附件
	 * @param response
	 * @param ids ,  id ,逗号隔开
	 * @throws IOException 
	 */
	@RequestMapping(value="/downLoadByIds")
	public void downLoadByIds(HttpServletResponse response,String ids) throws IOException{
		if(ids == null || "".equals(ids)) return;
		Attachment query = new Attachment();
		query.setAppendWhere(" and id in (" + ids + ")");
		List<Attachment> list = service.getList(query);
		responseAttachment(response, list);
	}
	
	/**
	 * 下载一组附件
	 * @param response
	 * @param groupId
	 * @throws IOException 
	 */
	@RequestMapping(value="/downLoadGroup")
	public void downLoadByGroupId(HttpServletResponse response,String groupId) throws IOException{
		if(groupId == null || "".equals(groupId)) return;
		Attachment query = new Attachment();
		query.setGroupId(groupId);
		List<Attachment> list = service.getList(query);
		responseAttachment(response, list);
	}
	
	
	@RequestMapping(value="/upload")
	public String upload(@RequestParam("attach") MultipartFile attach,Attachment attachPojo,Map<String,Object> result) {
		try {
			//attachPojo.getF
			StringBuffer basePath = new StringBuffer(getBasePath());
			if(ServletFileUpload.isMultipartContent(getRequest())) {
				String folder = attachPojo.getFileFolder();
				String filename = attach.getOriginalFilename();
				if(!StrUtil.isNullOrEmpty(folder)) {
					if(folder.startsWith("/")) folder = folder.substring(folder.indexOf("/") + 1);
					if(folder.endsWith("/")) folder =  folder.substring(0,folder.lastIndexOf("/"));
					folder += "/" + new SimpleDateFormat("yyyyMM").format(new Date());
					attachPojo.setFileFolder(folder);
				} else {
					folder = new SimpleDateFormat("yyyyMM").format(new Date());
					attachPojo.setFileFolder(folder);
				}
				String fileext = filename.substring(filename.lastIndexOf("."));
				attachPojo.setFileName(UUID.randomUUID() + fileext);
				attachPojo.setFileRealName(attach.getOriginalFilename());//原始文件名
				attachPojo.setFileSize(attach.getSize());
				attachPojo.setCreateTime(new Date());
				//attachPojo.setUploadName("龙运涛");
				service.save(attachPojo);
				
				File baseFile = new File(basePath + folder + File.separator);
				if(!baseFile.exists()) baseFile.mkdirs();
				FileCopyUtils.copy(attach.getBytes(), new File(baseFile,attachPojo.getFileName()));
				result.put("attach", attachPojo);
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new RuntimeException(e.getMessage(), e);
		}
		return folder_name + "/attachmentUpload";
	}

	private void responseAttachment(HttpServletResponse response,List<Attachment> list) throws IOException{
		if(list == null || list.size() == 0) return;
		if(list.size() == 1) {
			dowloadOne(response,list.get(0));
		} else {
			ZipOutputStream zipOut = null;
			FileInputStream in = null;
			try {
			//	StringBuffer basePath = new StringBuffer(getBasePath());
				String dloadFileName = "";
				for (Attachment attachment : list) {
					dloadFileName+= attachment.getSimpleFileName() + "&";
					if(dloadFileName.length() > 30) break; 
				}
				dloadFileName = dloadFileName.substring(0, dloadFileName.length() -1);
				dloadFileName += ".zip";
				response.reset();
				response.addHeader("Content-Disposition", "attachment;filename=" + getDownName(dloadFileName));
				response.setContentType("application/zip;charset=utf-8");
				zipOut = new ZipOutputStream(new BufferedOutputStream(response.getOutputStream()));
				//创建压缩文件
				zipOut.setEncoding("GBK");
				Long length = 0L;
				for (Attachment attachment : list) {
					//String filPath = basePath + attachment.getFileLocation();
					in = new FileInputStream(new File(getBasePath(),attachment.getFileLocation()));
					byte [] buff = new byte[1024];
					int len = 0;
					length += in.available();
					ZipEntry zipE = new ZipEntry(attachment.getFileRealName());
					zipOut.putNextEntry(zipE);
				//	if(in == null) continue;
					while((len = in.read(buff)) != -1) {
						zipOut.write(buff, 0, len);
						buff = new byte[1024];
						zipOut.flush();
					}
					zipOut.closeEntry();
					in.close();
				}
				response.setContentLength(length.intValue());
				zipOut.flush();
			} catch (Exception e){
				log.error(e);
			} finally {
				if(in != null) in.close();
				if(null != zipOut) zipOut.close();
			}
		}
	}
	
	private void dowloadOne(HttpServletResponse response, Attachment attachment) throws IOException{
		StringBuffer basePath = new StringBuffer(getBasePath());
		File filePath = new File(basePath.toString());
		if(!filePath.exists()) {
			filePath.mkdirs();
		}
		File newFile = new File(filePath,attachment.getFileLocation());
		InputStream fis = null;
		OutputStream toClient = null;
		try {
			// 以流的形式下载文件。
			fis = new FileInputStream(newFile);
			String dloadFileName = attachment.getFileRealName();
			response.addHeader("Content-Disposition", "attachment;filename=" + getDownName(dloadFileName));
			response.setContentType("application/x-download;charset=utf-8");
			response.setContentLength(fis.available());
			 toClient = response.getOutputStream();
			byte[] buffer = new byte[1024];
			int len = 0;
			while ((len = fis.read(buffer)) != -1) {
				toClient.write(buffer,0,len);
			}
			
      // response.addHeader("Content-Length", "" + newFile.length());
			toClient.flush();
		} finally {
			if(fis != null) fis.close();
		}
	}
	

	private String getDownName(String name) throws UnsupportedEncodingException {
		name = name.replaceAll(" ", "");
		return new String(name.getBytes("gbk"), "iso-8859-1");
	}
	
	@Override
	protected void beforeListData(Attachment vo) {
		
	}



	protected String editHandler(Map<String, Object> result) {
		return folder_name + "/attachmentEdit";
	}

	protected String showListHandler(Attachment vo,Map<String, Object> result) {
		return folder_name + "/attachmentList";
	} 
}
