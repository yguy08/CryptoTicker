package com.speculation1000.cryptoticker.core;

import com.speculation1000.cryptoticker.poloniex.PoloniexTickProducer;

public interface ITickProducer {
    void produce() throws InterruptedException;
    
    static ITickProducer createProducer(String producer, ITickQueue queue) {
    	switch(producer) {
    	case "Poloniex":
    		return new PoloniexTickProducer(producer,queue);
    	case "Bittrex":
    		return null;
    	default:
    		return null;
    	}
    }
}
