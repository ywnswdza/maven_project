package com.losy.test.fileprocess;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Queue;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.losy.userinfo.dao.IUserInfoDao;
import com.losy.common.AbstractBaseTest;

@ContextConfiguration(locations={"classpath:applicationContext-db-extend.xml"})
public class ParseIpFile extends AbstractBaseTest{

	private static Pattern ips = Pattern.compile("(\\d{1,3}\\.){3}\\d{1,3}");
	
	private static Queue<String> dataQueue = new ConcurrentLinkedQueue<String>();

	@Autowired
	private IUserInfoDao dao ;
	
	private ScheduledExecutorService pools = null;
	
	
	@Before
	public void setUp (){
		pools = Executors.newScheduledThreadPool(20);
		//pools.scheduleWithFixedDelay(new DataQueueTask(), 0, 10, TimeUnit.MILLISECONDS);
		//pools.s
	}
	
/*	@After
	public void setDown(){
		pools.shutdown();
	}*/
	
	@Test
	public void mains() throws IOException, InterruptedException {
		FileInputStream file = new FileInputStream(System.getProperty("user.dir") + "/test/ipfiles.txt");
		BufferedReader br = new BufferedReader(new InputStreamReader(file,"GBK"));
		//byte [] buffered = new 
		String line = null;
		while ((line = br.readLine()) != null) {
			processLine(line);
		}
		pools.scheduleWithFixedDelay(new Runnable() {
			@Override
			public void run() {
				pools.execute(new Runnable() {
					@Override
					public void run() {
						saveDb();
					}
				});
			}
		}, 0, 100, TimeUnit.MILLISECONDS);
		
		Thread.sleep(1000 * 60 * 10);
	}

	
	public void saveDb(){
		Connection conn = dao.getConnnection(this.getClass());
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement("INSERT INTO `t_ip_repositor` (`startIp`,`startLongIp`, `endIp`,  `endLongIp`, `address`,`operator`) values (?,?,?,?,?,?)");
			String results = null;
			int i = 0;
			while ((results = dataQueue.poll()) != null) {
				String[] data = results.split("#");
				pstmt.setString(1,data[0]);
				pstmt.setString(2,data[1]);
				pstmt.setString(3,data[2]);
				pstmt.setString(4,data[3]);
				pstmt.setString(5,data[4]);
				pstmt.setString(6,data[5]);
				pstmt.addBatch();
				i++;
				if(i >= 10000) {
					break;
				}
			}
			log.info("insert batch 10000 begin ");
			pstmt.executeBatch();
			log.info("insert batch 10000 success ");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dao.close(pstmt);
			dao.close(conn);
		}
	}
	

	@Test
	public void testQuery(){
		Connection conn = dao.getConnnection(this.getClass());
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			long stTime = System.currentTimeMillis();
			
			//List<Long> list = new ArrayList<Long>();
			TreeMap<Long,String> tmap = new TreeMap<Long,String>();
			
			logRuntimeInfo();
			
			pstmt = conn.prepareStatement("select * from t_ip_repositor order by startLongIp asc ");
			rs = pstmt.executeQuery();
			Runtime.getRuntime().freeMemory();
			log.info("query all t_ip_repositor use time " + (System.currentTimeMillis() - stTime) + "ms");
			
			logRuntimeInfo();
			
			while(rs.next()) {
				//list.add(rs.getLong("startLongIp"));
				tmap.put(rs.getLong("startLongIp"),rs.getString("address"));
			}
			
			
			
			log.info("append to list use time " + (System.currentTimeMillis() - stTime) + "ms");
			
			logRuntimeInfo();
			
			
			long key = tmap.lowerKey(1196233222L);
			
			log.info(tmap.get(key));
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dao.close(rs);
			dao.close(pstmt);
			dao.close(conn);
			
			logRuntimeInfo();
		}
		
	}
	
	private void logRuntimeInfo(){
		log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		log.info("maxMemory " + Runtime.getRuntime().maxMemory() / 1024 / 1024 + "MB");
		log.info("totalMemory " + Runtime.getRuntime().totalMemory() / 1024 / 1024  + "MB");
		log.info("freeMemory " + Runtime.getRuntime().freeMemory() / 1024 / 1024  + "MB");
		log.info("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
	}
	
	private static void processLine(String line) {
		//223.255.255.0   223.255.255.255  澳大利亚  CZ88.NET
		if(StringUtils.isBlank(line)) return;
		Matcher ipmater =  ips.matcher(line);
		ipmater.find();
		String startIp = ipmater.group(0); // System.out.print( + " ");
		ipmater.find();
		//System.out.println(ipmater.group(0));
		String endIp = ipmater.group(0);
		
		line = line.replaceAll("(\\d{1,3}\\.){3}\\d{1,3}","");
		line = line.trim();
		String[] linesss = line.split("\\s{1,}");
		String address = "";
		String yys = "";
		if(linesss.length > 2) {
			for (int i = 1; i < linesss.length; i++) {
				yys += linesss[i] + " ";
			}
			yys.trim();
			address = linesss[0];
		} else if(linesss.length == 1) {
			address = yys = linesss[0];
		} else {
			address = linesss[0];
			yys = linesss[1];
		}
	//	System.out.println(startIp + " | " + endIp + " | " + address + " | " + yys);
		//System.out.println(linesss);
		String results = startIp + "#" + processIp(startIp) + "#" + endIp +  "#" + processIp(endIp) + "#" + address + "#" + yys;
		dataQueue.add(results);
	}
	
	private static String processIp(String ip){
		String longIp = "";
		String[] ipd = ip.split("\\.");
		for(String d : ipd) {
			for(int i = d.length() ; i < 3 ; i++) {
				longIp += "0";
			}
			longIp += d;
			//longIp += Integer.toBinaryString(Integer.parseInt(d));
			//longIp += ".";
		}
		return longIp;
	}
	
	public static void main(String[] args) {
		System.out.println(processIp("255.254.1.1"));
		System.out.println(Runtime.getRuntime().maxMemory() / 1024 / 1024);
		System.out.println(Runtime.getRuntime().totalMemory() / 1024 / 1024 );
		System.out.println(Runtime.getRuntime().freeMemory() / 1024 / 1024 );
		System.out.println(processIp("255.254.1.1"));
	}
	
}
