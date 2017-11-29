package com.speculation1000.cryptoticker.model;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;

import com.lmax.disruptor.dsl.Disruptor;

public class XchangeDataFeed extends Tape {

	private static final Logger LOGGER = LogManager.getLogger("XchangeDataFeed");
	
	protected static Exchange EXCHANGE = null;
		
	public XchangeDataFeed(){
		disruptor = new Disruptor<>(Tick::new, bufferSize, Executors.defaultThreadFactory());
		tickEvents = new ArrayList<>();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public void configure(String path) throws Exception {
        config = new Properties();
        config.load(new FileInputStream(path));
        
        tickEvents.stream().forEach(e -> disruptor.handleEventsWith(e::onTick));
		disruptor.start();
		ringBuffer = disruptor.getRingBuffer();
        EXCHANGE = ExchangeFactory.INSTANCE.createExchange(config.getProperty("exchange"));
    }

	@Override
	public void start() throws Exception {
        while(true) {
        	try {
        		
        		Stream.of(config.getProperty("symbols").split(";")).forEach(s -> 
        				{
							try {
								ringBuffer.publishEvent(tickEvent::translate,
										EXCHANGE.getMarketDataService().getTicker(new CurrencyPair(s)));
								Thread.sleep(1000);
							} catch (NotAvailableFromExchangeException | NotYetImplementedForExchangeException
									| ExchangeException | IOException | InterruptedException e) {
								LOGGER.error("ERROR");
								try {
									Thread.sleep(10000);
								} catch (InterruptedException e1) {
									LOGGER.error("ERROR");								
								}
							}
						});
	        }catch(Exception e) {
        		LOGGER.error(e);
        		Thread.sleep(10000);
        	}
        }		
	}

}
