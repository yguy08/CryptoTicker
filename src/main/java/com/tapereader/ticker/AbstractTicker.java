package com.tapereader.ticker;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;

import com.google.inject.Inject;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.tapereader.framework.DisruptorClerk;
import com.tapereader.framework.Tick;
import com.tapereader.framework.Ticker;
import com.tapereader.framework.Transmitter;

public abstract class AbstractTicker implements Ticker {
    
    protected final Disruptor<Tick> disruptor;
    
    protected final RingBuffer<Tick> ringBuffer;
    
    protected final ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
    
    protected final Transmitter transmitter;
    
    protected AtomicBoolean running = new AtomicBoolean(false);
    
    @Inject
    public AbstractTicker(Transmitter transmitter) {
        this.transmitter = transmitter;
        disruptor = DisruptorClerk.newTickDisruptor();
        ringBuffer = disruptor.getRingBuffer();
        handleEventsWith(disruptor);
    }
    
    public abstract void handleEventsWith(Disruptor disruptor);

}
