package com.losy.codegen.utils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import org.apache.log4j.Logger;

import com.mysql.jdbc.Statement;

public class DBUtils {

	private static Properties pros;
	private static final Logger log = Logger.getLogger(DBUtils.class);
	private static  String url = "";
	private static  String username = "";
	private static  String pwd = "";
	private static  String driver = "";
	
	static {
		pros = new Properties();
		try {
			pros.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("db.properties"));
			url = pros.getProperty("db.url", "jdbc:mysql://localhost:3306/test?useUnicode=true&amp;characterEncoding=utf8");
			username = pros.getProperty("db.user", "root");
			pwd = pros.getProperty("db.pwd", "123456");
			driver = pros.getProperty("db.driver", "com.mysql.jdbc.Driver");
			Class.forName(driver);
		} catch (IOException e) {
			log.error(e);
		} catch (ClassNotFoundException e) {
			log.error(e);
		}
	}
	
	public static Connection getConn(){
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url, username, pwd);
		} catch (SQLException e) {
			log.error(e);
		}
		return conn;
	}
	
	public static void close(Object obj) {
		if(obj == null) return;
		if(obj instanceof Connection) {
			try {
				Connection conn = Connection.class.cast(obj);
				conn.close();
				conn = null;
			} catch (SQLException e) {
				log.error(e);
			}
		}
		if(obj instanceof Statement) {
			try {
				Statement st = Statement.class.cast(obj);
				st.close();
				st = null;
			} catch (SQLException e) {
				log.error(e);
			}
		}
	}
}
