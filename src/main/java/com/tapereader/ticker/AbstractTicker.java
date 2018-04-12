package com.tapereader.ticker;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;

import com.google.inject.Inject;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.tapereader.framework.Tape;
import com.tapereader.framework.Ticker;
import com.tapereader.model.Tick;
import com.tapereader.util.DisruptorClerk;

@SuppressWarnings("unchecked")
public abstract class AbstractTicker implements Ticker {
    
    protected final Disruptor<Tick> disruptor;
    
    protected final RingBuffer<Tick> ringBuffer;
    
    protected AtomicBoolean running = new AtomicBoolean(false);
    
    protected final ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
    
    @Inject
    public AbstractTicker(Tape tape) {
        disruptor = DisruptorClerk.createDefaultMarketEventDisruptor();
        ringBuffer = disruptor.getRingBuffer();
        disruptor.handleEventsWith(tape::onEvent);
    }

}
