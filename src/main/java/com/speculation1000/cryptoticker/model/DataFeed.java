package com.speculation1000.cryptoticker.model;

public interface DataFeed {
	
	void configure(String path) throws Exception;
	
	void start() throws Exception;

}
