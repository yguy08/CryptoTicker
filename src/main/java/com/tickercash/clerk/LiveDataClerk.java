package com.tickercash.clerk;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.ExceptionHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.tickercash.marketdata.MarketEvent;

public abstract class LiveDataClerk {
        
    protected static final int BUFFER = 1024;
    
    protected final Disruptor<MarketEvent> disruptor;
    
    protected final RingBuffer<MarketEvent> ringBuffer;

    protected final List<String> subscriptions;

    public LiveDataClerk() {
        disruptor = MarketEvent.createDefaultMarketEventDisruptor();
        disruptor.setDefaultExceptionHandler(new ExceptionHandler<MarketEvent>(){

			@Override
			public void handleEventException(Throwable ex, long sequence, MarketEvent event) {
				System.out.println(ex.getMessage());
			}

			@Override
			public void handleOnStartException(Throwable ex) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void handleOnShutdownException(Throwable ex) {
				// TODO Auto-generated method stub
				
			}
        	
        });
        ringBuffer = disruptor.getRingBuffer();
        subscriptions = new ArrayList<>();
    }
    
    public void subscribe(String symbol) {
        subscriptions.add(symbol);      
    }
    
    public void unsubscribe(String symbol) {
        subscriptions.removeIf((s) -> s.equals(symbol));        
    }
    
    @SuppressWarnings("unchecked")
    public void addHandler(EventHandler<MarketEvent>... handler) {
        disruptor.handleEventsWith(handler);
    }
                
    public abstract void start() throws Exception;

}
