package com.speculation1000.cryptoticker.poloniex;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.h2.tools.Csv;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.poloniex.PoloniexExchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.speculation1000.cryptoticker.core.GenericTickImpl;
import com.speculation1000.cryptoticker.core.ITickProducer;
import com.speculation1000.cryptoticker.core.ITickQueue;
import com.speculation1000.cryptoticker.core.UniqueCurrentTimeMS;

public class PoloniexTickProducer implements ITickProducer {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PoloniexTickProducer.class);

	private static final Exchange POLONIEX = ExchangeFactory.INSTANCE.createExchange(PoloniexExchange.class.getName());
	
	private final ITickQueue queue;
	
	private final String name;
	
	public PoloniexTickProducer(String name,ITickQueue queue) {
		this.name = name;
		this.queue = queue;
	}

	@Override
	public void produce() throws InterruptedException {		
        try {
        	ResultSet rs = new Csv().read("poloniex.csv", null, null);
			while (rs.next()) {        
		        Ticker ticker = POLONIEX.getMarketDataService().getTicker(new CurrencyPair(rs.getString(1),rs.getString(2)));
				queue.put(new GenericTickImpl(ticker.getCurrencyPair().toString(),UniqueCurrentTimeMS.uniqueCurrentTimeMS(),
						ticker.getLast().doubleValue(),name));
		        Thread.sleep(1000);
			}
	        rs.close();
	    } catch (SQLException | NotAvailableFromExchangeException | NotYetImplementedForExchangeException | ExchangeException | IOException e1) {
	    	LOGGER.error(e1.getMessage());
	    	Thread.sleep(60000);
		}
	}

}
