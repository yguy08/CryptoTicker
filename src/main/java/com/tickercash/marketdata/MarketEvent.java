package com.tickercash.marketdata;

import java.util.concurrent.Executors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.EventTranslatorOneArg;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

public class MarketEvent {
		
	private MarketEvent event;
	
    private static final Logger LOGGER = LogManager.getLogger("MarketEvent");

	
	public void set(MarketEvent e){
		this.event = e;
	}
	
	public MarketEvent get(){
		return event;
	}
	
	public static final Disruptor<MarketEvent> createDefaultMarketEventDisruptor(){
		return new Disruptor<MarketEvent>(MarketEvent.MARKET_EVENT_FACTORY, 1024, Executors.defaultThreadFactory(),
                ProducerType.SINGLE, new BlockingWaitStrategy());
	}
	
	public static final EventFactory<MarketEvent> MARKET_EVENT_FACTORY = 
			() -> new MarketEvent();
	
	public static final EventHandler<MarketEvent> MARKET_EVENT_LOGGER = new EventHandler<MarketEvent>(){
		
		@Override
		public void onEvent(MarketEvent event, long sequence, boolean endOfBatch) throws Exception {
			LOGGER.info(event.get().toString());
		}
	};
			
	public static final EventTranslatorOneArg<MarketEvent,Object[]> TRANSLATOR_SYMBOL_TS_LAST 
			= (MarketEvent event, long sequence, Object... args) -> event.set(Tick.set((String) args[0], (Long) args[1], (Double) args[2]));
		

}
