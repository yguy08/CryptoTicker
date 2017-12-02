package com.speculation1000.cryptoticker.tape;

import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;

import com.speculation1000.cryptoticker.event.handler.TickEventHandler;
import com.speculation1000.cryptoticker.tapereader.TapeReader;

public class XchangeLiveTape extends Tape {

    private static final Logger LOGGER = LogManager.getLogger("XchangeDataFeed");
    
    @Override
    public void start() throws Exception {
        disruptor.start();
        while(true) {
            for(String symbol : symbols){
                onData(TapeReader.TICK_FACTORY
                .apply(symbol, EXCHANGE
                .getMarketDataService()
                .getTicker(new CurrencyPair(symbol))));
                Thread.sleep(1000);
          }
	   }
    }

	@Override
	public Tape subscribe(String symbol) {
		Stream.of(symbol).forEach(e -> this.symbols.add(e));
		return this;
	}

	@Override
	public Tape addTickEventHandler(TickEventHandler handler) {
		disruptor.handleEventsWith(handler::onTick);
		return this;
	}

	@Override
	public void setExchange(Exchange exchange) {
		EXCHANGE = exchange;
	};
	
}
