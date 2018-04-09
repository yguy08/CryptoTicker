package com.tickercash.tapereader.ticker;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;

import com.google.inject.Inject;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.tickercash.tapereader.framework.Transmitter;
import com.tickercash.tapereader.model.Tick;
import com.tickercash.tapereader.util.DisruptorClerk;

@SuppressWarnings("unchecked")
public abstract class AbstractQuoteBoy implements QuoteBoy {
    
    protected final Disruptor<Tick> disruptor;
    
    protected final RingBuffer<Tick> ringBuffer;
    
    protected AtomicBoolean running = new AtomicBoolean(false);
    
    protected final ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
    
    @Inject
    public AbstractQuoteBoy(Transmitter transmitter) {
        disruptor = DisruptorClerk.createDefaultMarketEventDisruptor();
        ringBuffer = disruptor.getRingBuffer();
        disruptor.handleEventsWith(transmitter);
    }

}
