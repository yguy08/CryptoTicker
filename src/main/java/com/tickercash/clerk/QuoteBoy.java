package com.tickercash.clerk;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.tickercash.clerk.cmc.CMCQuoteBoy;
import com.tickercash.clerk.polo.PoloQuoteBoy;
import com.tickercash.enums.QuoteBoyType;
import com.tickercash.marketdata.Tick;

public abstract class QuoteBoy {
    
    protected static final int BUFFER = 1024;
    
    protected final Disruptor<Tick> disruptor;
    
    protected final RingBuffer<Tick> ringBuffer;

    protected final List<String> subscriptions;
    
    protected int throttle = 1000;
    
    protected final ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

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
    public void addHandler(EventHandler<Tick>... handler) {
        disruptor.handleEventsWith(handler);
    }
    
    public abstract String getName();
    
    public abstract void start() throws Exception;
    
    public static final QuoteBoy createQuoteBoy(String dataSource){
        QuoteBoy dataClerk = null;
        switch(QuoteBoyType.valueOf(dataSource.toUpperCase())) {
            case CMC:
                dataClerk = new CMCQuoteBoy();
                break;
            case POLONIEX:
                dataClerk = new PoloQuoteBoy();
                break;
            case GDAX:
            case FAKE:
                dataClerk = new FakeTicker();
                break;
            default:
                dataClerk = new FakeTicker();
                break;
        }
        return dataClerk;
    }

}
