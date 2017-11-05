package com.speculation1000.cryptoticker.app;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.h2.tools.Csv;

import com.speculation1000.cryptoticker.core.GenericTickQueueImpl;
import com.speculation1000.cryptoticker.core.ITickConsumer;
import com.speculation1000.cryptoticker.core.ITickProducer;
import com.speculation1000.cryptoticker.core.ITickQueue;

public class App {
	
	private List<String> exchanges;
	
	public App() throws Exception {	
		exchanges = new ArrayList<>();
	    ResultSet rs = new Csv().read("config/exchange.csv", null, null);
        
	    while (rs.next()) {
        	exchanges.add(rs.getString(1));
        }
        rs.close();
	}
	
	private void start() {
		ITickQueue tickQueue = new GenericTickQueueImpl();
		
		ExecutorService executorService = Executors.newFixedThreadPool(5);
		
        for(String exchange : exchanges) {

    		final ITickProducer producer = ITickProducer.createProducer(exchange, tickQueue);
    	      executorService.submit(() -> {
    	        while (true) {
    	          producer.produce();
    	        }
    	      });   

    	    final ITickConsumer consumer = ITickConsumer.createConsumer(exchange, tickQueue);
		    executorService.submit(() -> {
		    	while(true) {
		    		consumer.consume();
		    	}
		    });
        	
        }
	}

	public static void main(String[] args) throws SQLException {
		try {
			new App().start();
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}

}
