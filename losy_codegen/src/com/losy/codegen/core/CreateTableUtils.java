package com.losy.codegen.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import com.losy.codegen.utils.DBUtils;
import com.losy.codegen.utils.ReadPropertiesUtil;

public class CreateTableUtils {

	private static final Set<String> files = new HashSet<String>();
	private static final Logger log = Logger.getLogger(CreateTableUtils.class);
	
	public static void addFile (String file) {
		files.add(file);
	}

	public static void createTables() {
		if(files.size() == 0) return;
		Connection conn = null;
		BufferedReader read = null;
		Statement stmt = null;
		try {
			conn = DBUtils.getConn();
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			StringBuffer sql = new StringBuffer();
			for (String filePath : files) {
				sql.setLength(0);
				try {
					read = new BufferedReader(new FileReader(new File(filePath)));
					String line = "";
					while ((line = read.readLine()) != null) {
						if(line.toLowerCase().startsWith("drop")) {
							log.info(line.trim());
							if(ReadPropertiesUtil.getBooleanValue("gen.createTableBeforeDrop")) stmt.addBatch(line.trim());
						} else {
							sql.append(line.trim());
						}
					}
					sql.trimToSize();
					log.info(sql);
					stmt.addBatch(sql.toString());
				} catch (IOException e) {
					log.error(e);
				}
			}
			stmt.executeBatch();
			conn.commit();
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				log.error(e1);
			}
			log.error(e);
		} finally {
			try { if(conn != null) conn.setAutoCommit(true); } catch (SQLException e1) {}
			DBUtils.close(stmt);
			DBUtils.close(conn);
			if(read != null) try { read.close(); }catch (IOException e) {}
		}
	}
}
