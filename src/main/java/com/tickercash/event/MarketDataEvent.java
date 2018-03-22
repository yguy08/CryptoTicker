package com.tickercash.event;

import com.lmax.disruptor.EventFactory;
import com.tickercash.marketdata.MarketDataEventVO;

public class MarketDataEvent {
    private MarketDataEventVO value;
    
    public void set(MarketDataEventVO value){
        this.value = value;
    }
    
    public MarketDataEventVO get(){
        return this.value;
    }
    
    public static final EventFactory<MarketDataEvent> MARKET_EVENT_FACTORY = () -> new MarketDataEvent();
}
