package com.tickercash.tapereader.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class EmbeddedH2Db {
	
	private String connectionStr;
	private Connection conn;
	
	public EmbeddedH2Db(String dataSource) {
		connectionStr = dataSource;
		try {
			Class.forName("org.h2.Driver");
			conn = DriverManager.getConnection(connectionStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Connection getConnection() {
		return conn;
	}

}
