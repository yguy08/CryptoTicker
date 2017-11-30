package com.speculation1000.cryptoticker.tape;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Properties;
import java.util.concurrent.Executors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;

import com.lmax.disruptor.dsl.Disruptor;
import com.speculation1000.cryptoticker.core.UniqueCurrentTimeMS;
import com.speculation1000.cryptoticker.event.Tick;
import com.speculation1000.cryptoticker.event.handler.TickEventHandlerImpl;

public class XchangeLiveTape extends Tape {

	private static final Logger LOGGER = LogManager.getLogger("XchangeDataFeed");
	
	protected static Exchange EXCHANGE = null;
		
	public XchangeLiveTape(){
		disruptor = new Disruptor<>(Tick::new, bufferSize, Executors.defaultThreadFactory());
		tickEvents = new ArrayList<>();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public void configure(String path) throws Exception {
        config = new Properties();
        config.load(new FileInputStream(path));
        
        tickEvents.add(new TickEventHandlerImpl());
        tickEvents.stream().forEach(e -> disruptor.handleEventsWith(e::onTick));
        
		disruptor.start();
		
		ringBuffer = disruptor.getRingBuffer();
        
		EXCHANGE = ExchangeFactory.INSTANCE.createExchange(config.getProperty("exchange"));
    }

	@Override
	public void start() throws Exception {
		while(true) {
			final Ticker xChangeTicker = EXCHANGE.getMarketDataService().getTicker(new CurrencyPair(config.getProperty("symbol","BTC/USDT")));
			final double[] money = {xChangeTicker.getLast().doubleValue(),xChangeTicker.getBid().doubleValue(),
					xChangeTicker.getAsk().doubleValue(),xChangeTicker.getVolume().intValue()};
        	onData(config.getProperty("symbol", "BTC/USDT"),UniqueCurrentTimeMS.uniqueCurrentTimeMS(),money);
        	Thread.sleep(1000);
	   }
    }
	
}
