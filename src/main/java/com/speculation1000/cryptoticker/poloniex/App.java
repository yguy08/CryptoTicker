package com.speculation1000.cryptoticker.poloniex;

import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.poloniex.PoloniexExchange;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.speculation1000.cryptoticker.disruptor.TickEventFactory;
import com.speculation1000.cryptoticker.disruptor.TickEventHandler;
import com.speculation1000.cryptoticker.disruptor.TickEventProducer;
import com.speculation1000.cryptoticker.marketdataevent.Tick;

public class App {
	
	private static final Logger logger = LogManager.getLogger("TickEventHandler");
	
	private static final Exchange POLONIEX = ExchangeFactory.INSTANCE.createExchange(PoloniexExchange.class.getName());
	
	public static void main(String[] args) throws InterruptedException, SQLException, 
	NotAvailableFromExchangeException, NotYetImplementedForExchangeException, ExchangeException, IOException {
		
		ExecutorService executorService = Executors.newFixedThreadPool(5);
	
		// Executor that will be used to construct new threads for consumers
		ThreadFactory threadFactory = Executors.defaultThreadFactory();
		
		// The factory for the event
		TickEventFactory factory = new TickEventFactory();
		
		// Specify the size of the ring buffer, must be power of 2.
		int bufferSize = 1024;
		
		// Construct the Disruptor
		Disruptor<Tick> disruptor = new Disruptor<>(factory, bufferSize, threadFactory);
		
		// Connect the handler
		disruptor.handleEventsWith(new TickEventHandler("main"));
		
		// Start the Disruptor, starts all threads running
		disruptor.start();
		
		// Get the ring buffer from the Disruptor to be used for publishing.
		RingBuffer<Tick> ringBuffer = disruptor.getRingBuffer();
		
		TickEventProducer producer = new TickEventProducer(ringBuffer);
		
		executorService.submit(() -> {
			while(true){
				Ticker ticker = POLONIEX.getMarketDataService().getTicker(new CurrencyPair(Currency.BTC, Currency.USDT));
				producer.onData(ticker);
				Thread.sleep(1000);
			}			
		});
		
		//executorService.submit(() -> {
			Consumer consumer = new Consumer();
			try {
				consumer.consume();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		//});
		
	}

}
