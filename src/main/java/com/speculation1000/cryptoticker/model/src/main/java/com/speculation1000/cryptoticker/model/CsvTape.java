package com.speculation1000.cryptoticker.model;

import java.io.FileInputStream;
import java.sql.ResultSet;
import java.util.Properties;

import org.h2.tools.Csv;

public class CsvTape extends Tape {

	@Override
	public void configure(String path) throws Exception {
        config = new Properties();
        config.load(new FileInputStream(path));
	}

	@Override
	public void start() throws Exception {
        ResultSet rs = new Csv().read(config.getProperty("file"), null, null);
        while (rs.next()) {
            System.out.println(rs.getString(1)+","+rs.getLong(2)+","+rs.getDouble(3)+","+rs.getDouble(4)+","
            		+rs.getDouble(5)+","+rs.getDouble(6)+","+rs.getDouble(7));
        }
        rs.close();
	}

}
