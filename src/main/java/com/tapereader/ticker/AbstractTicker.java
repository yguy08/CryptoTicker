package com.tapereader.ticker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;

import com.google.inject.Inject;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.tapereader.framework.DisruptorClerk;
import com.tapereader.framework.Ticker;
import com.tapereader.framework.Transmitter;
import com.tapereader.model.Tick;

public abstract class AbstractTicker implements Ticker {
    
    protected final Disruptor<Tick> disruptor;
    
    protected final RingBuffer<Tick> ringBuffer;
    
    protected final ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
    
    protected final Transmitter transmitter;
    
    protected AtomicBoolean running = new AtomicBoolean(false);
    
    protected final List<String> SYMBOLS;
    
    @Inject
    public AbstractTicker(Transmitter transmitter) {
        this.transmitter = transmitter;
        disruptor = DisruptorClerk.newTickDisruptor();
        ringBuffer = disruptor.getRingBuffer();
        handleEventsWith(disruptor);
        SYMBOLS = new ArrayList<>();
    }
    
    public abstract void handleEventsWith(Disruptor disruptor);
    
    @Override
    public void subscribe(String symbol){
        SYMBOLS.add(symbol);
    }
    
    @Override
    public void subscribe(String... symbols){
        SYMBOLS.addAll(Arrays.asList(symbols));
    }
    
    @Override
    public void stop() {
        
    }

}
