package com.speculation1000.cryptoticker.tape;

import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;

import com.speculation1000.cryptoticker.core.UniqueCurrentTimeMS;
import com.speculation1000.cryptoticker.event.handler.TickEventHandler;

public class XchangeLiveTape extends Tape {

	private static final Logger LOGGER = LogManager.getLogger("XchangeDataFeed");

	@Override
	public void start() throws Exception {
		disruptor.start();
		while(true) {
			for(String symbol : symbols){
				final Ticker xChangeTicker = EXCHANGE.getMarketDataService().getTicker(new CurrencyPair(symbol));
				final double[] money = {xChangeTicker.getLast().doubleValue(),xChangeTicker.getBid().doubleValue(),
						xChangeTicker.getAsk().doubleValue(),xChangeTicker.getVolume().intValue()};
	        	onData(symbol,UniqueCurrentTimeMS.uniqueCurrentTimeMS(),money);
	        	Thread.sleep(1000);
			}
	   }
    }

	@Override
	public void subscribe(String... symbol) {
		Stream.of(symbol).forEach(e -> this.symbols.add(e));
	}

	@Override
	public void addTickEventHandler(TickEventHandler... handler) {
		disruptor.handleEventsWith(handler[0]::onTick);
	}

	@Override
	public void setExchange(Exchange exchange) {
		EXCHANGE = exchange;
	}
	
}
