package com.speculation1000.cryptoticker.tape;

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
        final ResultSet rs = new Csv().read(config.getProperty("file"), null, null);
        while (rs.next()) {
        	final double[] tickArr = {rs.getDouble(3),rs.getDouble(4),rs.getDouble(5)}; 
            onData(rs.getString(1),rs.getLong(2),tickArr);
        }
        rs.close();
	}

}
