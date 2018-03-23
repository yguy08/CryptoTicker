package com.tickercash.clerk;

import java.util.ArrayList;
import java.util.List;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.tickercash.event.MarketDataEvent;

public abstract class QuoteBoy {
    
    protected static final int BUFFER = 1024;
    
    protected final Disruptor<MarketDataEvent> disruptor;
    
    protected final RingBuffer<MarketDataEvent> ringBuffer;

    protected final List<String> subscriptions;

    public QuoteBoy() {
        disruptor = DisruptorClerk.createDefaultMarketEventDisruptor();
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
    public void addHandler(EventHandler<MarketDataEvent>... handler) {
        disruptor.handleEventsWith(handler);
    }
    
    public abstract void start() throws Exception;

}
