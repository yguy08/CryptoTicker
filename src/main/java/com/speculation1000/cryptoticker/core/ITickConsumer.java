package com.speculation1000.cryptoticker.core;

import com.speculation1000.cryptoticker.poloniex.PoloniexTickConsumer;

public interface ITickConsumer {
	void consume() throws InterruptedException, Exception;
	
    static ITickConsumer createConsumer(String producer, TickQueue queue) {
    	switch(producer) {
    	case "Poloniex":
    		return new PoloniexTickConsumer(queue);
    	case "Bittrex":
    		return null;
    	default:
    		return null;
    	}
    }
}
