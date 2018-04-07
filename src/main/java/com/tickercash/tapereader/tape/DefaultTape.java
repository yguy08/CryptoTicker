package com.tickercash.tapereader.tape;

import com.google.inject.Inject;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.tickercash.tapereader.clerk.DisruptorClerk;
import com.tickercash.tapereader.marketdata.Tick;
import com.tickercash.tapereader.tip.TipListener;

public abstract class DefaultTape implements Tape {
    
    protected final Disruptor<Tick> disruptor;
    
    protected final RingBuffer<Tick> ringBuffer;
    
    @Inject
    @SuppressWarnings("unchecked")
    protected DefaultTape(TipListener tip) {
        disruptor = DisruptorClerk.createDefaultMarketEventDisruptor();
        ringBuffer = disruptor.getRingBuffer();
        disruptor.handleEventsWith(tip);
        disruptor.start();
    }

}
