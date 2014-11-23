package com.losy.test.fileprocess;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ReloadFileTest {

	private ScheduledExecutorService service;
	
	private Map<String,String> fileMapping = new HashMap<String, String>();
	private List<File> files = new ArrayList<File>();
	public MessageDigest digest = null;
	
	
	public ReloadFileTest() throws NoSuchAlgorithmException {
		super();
		service = Executors.newSingleThreadScheduledExecutor();
		service.scheduleWithFixedDelay(new Task(), 0, 100, TimeUnit.MILLISECONDS);
		digest = MessageDigest.getInstance("MD5");
/*		InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("reloadfile.txt");
		in.available()*/
	}
	
	public void loadFiles(){
		File file = new File(System.getProperty("user.dir") + "/test/reloadfile.txt");
		if(file.exists()) {
			fileMapping.put(file.getName(), file.lastModified() + "|" + file.lastModified());
			files.add(file);
		}
	}
	
	public static void main(String[] args) throws NoSuchAlgorithmException {
		ReloadFileTest r = new ReloadFileTest();
		r.loadFiles();
	}
	
	class Task implements Runnable {
		public void run() {
			for (File f : files) {
				String currentValue = f.lastModified() + "|" + f.lastModified();
				if(!currentValue.equals(fileMapping.get(f.getName()))) {
					fileMapping.put(f.getName(), currentValue);
					System.out.println("文件[" + f.getName() + "]内修已改变 !!!!!");
				}
			}
		}
	}
}
