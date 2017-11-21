package com.speculation1000.cryptoticker.model;

import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.RingBuffer;
import java.util.concurrent.Executors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.poloniex.PoloniexExchange;

public class TickEventMain {
	
	private static final Exchange POLONIEX = ExchangeFactory.INSTANCE.createExchange(PoloniexExchange.class.getName());
	
	private static final Logger logger = LogManager.getLogger("TickEventMain");

    @SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception {

        // Specify the size of the ring buffer, must be power of 2.
        int bufferSize = 1024;

        // Construct the Disruptor
        Disruptor<Tick> disruptor = new Disruptor<>(Tick::new, bufferSize, Executors.defaultThreadFactory());

        // Connect the handler
        disruptor.handleEventsWith(TickEventHandler::logEvent,TickEventHandler::saveEvent);

        // Start the Disruptor, starts all threads running
        disruptor.start();

        // Get the ring buffer from the Disruptor to be used for publishing.
        RingBuffer<Tick> ringBuffer = disruptor.getRingBuffer();
        
        final CurrencyPair pair = new CurrencyPair("BTC/USDT");
        
        while(true) {
        	try {
	        	ringBuffer.publishEvent(TickEventTranslator::translate,(POLONIEX.getMarketDataService().getTicker(pair)));
	            Thread.sleep(1000);        		
        	}catch(Exception e) {
        		logger.error(e.getMessage());
        	}
        }
    }
}
