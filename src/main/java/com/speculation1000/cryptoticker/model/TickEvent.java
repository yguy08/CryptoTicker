package com.speculation1000.cryptoticker.model;

import org.knowm.xchange.dto.marketdata.Ticker;

public interface TickEvent {
    void onTick(Tick tick,long sequence, boolean endOfBatch) throws Exception;
    
    void translate(Tick event, long sequence, Ticker ticker);
}
