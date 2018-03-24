package com.tickercash.tapereader.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CsvWriter {
	
	private static final Logger LOGGER = LogManager.getLogger("CsvWriter");
	
	private BufferedWriter writer;
	
	private String file;
	
	public CsvWriter(String path) {
		file = path;
		try {
			writer = new BufferedWriter(new FileWriter(file));
		} catch (IOException e) {
			LOGGER.error(e);
		}
	}
	
	public void write(String str) {
		try {
			writer.write(str);
		} catch (IOException e) {
			LOGGER.error(e);
		}
	}

}
