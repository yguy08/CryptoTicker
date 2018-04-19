package com.tapereader.ticker;

import java.util.concurrent.atomic.AtomicBoolean;

import com.google.inject.Inject;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.tapereader.framework.MarketEvent;
import com.tapereader.framework.Ticker;
import com.tapereader.framework.Transmitter;
import com.tapereader.model.Tick;
import com.tapereader.util.DisruptorClerk;
import com.tapereader.util.UniqueCurrentTimeMS;

public class FakeTicker implements Ticker, EventHandler<MarketEvent> {
    
    protected final AtomicBoolean running = new AtomicBoolean(false);   
    
    private final Transmitter transmitter;
    
    private final Disruptor<MarketEvent> disruptor;
    
    private final RingBuffer<MarketEvent> ringBuffer;
    
    @Inject
    protected FakeTicker(Transmitter transmitter) {
    	this.transmitter = transmitter;
    	disruptor = DisruptorClerk.newMarketEventDisruptor();
    	ringBuffer = disruptor.getRingBuffer();
    	disruptor.handleEventsWith(this);
        disruptor.start();
    }

    @Override
    public void start() throws Exception {
        running.set(true);
        while(running.get()) {
            ringBuffer.publishEvent(this::translateTo);
            Thread.sleep(1000);
        }
    }
    
    private final void translateTo(MarketEvent event, long sequence){
        event.set(new Tick("BTC/USD", "FAKE", UniqueCurrentTimeMS.uniqueCurrentTimeMS(), 10000.00));
    }

	@Override
	public void onEvent(MarketEvent event, long sequence, boolean endOfBatch) throws Exception {
		transmitter.transmit((Tick) event.get());
	}

}
