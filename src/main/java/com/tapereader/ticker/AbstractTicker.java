package com.tapereader.ticker;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;

import com.google.inject.Inject;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.tapereader.framework.DisruptorClerk;
import com.tapereader.framework.Engine;
import com.tapereader.framework.Tick;
import com.tapereader.framework.Ticker;

@SuppressWarnings("unchecked")
public abstract class AbstractTicker implements Ticker {
    
    protected final Disruptor<Tick> disruptor;
    
    protected final RingBuffer<Tick> ringBuffer;
    
    protected AtomicBoolean running = new AtomicBoolean(false);
    
    protected final ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
    
    protected Engine tape;
    
    @Inject
    public AbstractTicker(Engine tape) {
        disruptor = DisruptorClerk.newTickDisruptor();
        ringBuffer = disruptor.getRingBuffer();
        //disruptor.handleEventsWith(tape);
    }

}
