package com.tapereader.framework;

import java.time.LocalDateTime;
import java.util.List;

import com.tapereader.model.Tick;
import com.tapereader.ticker.TickerType;
import com.tapereader.ticker.cmc.CMCHistoricalTicker;

public interface HistoricalTicker {
    
    List<Tick> getHistoricalTicks(String currency, LocalDateTime startDate, LocalDateTime endDate) throws Exception;
    
    static HistoricalTicker createHistoricalDataClerk(TickerType type) {
        switch(type) {
        case CMC:
            return new CMCHistoricalTicker();
        case POLONIEX:
        case GDAX:
        case FAKE:
        default:
            return new CMCHistoricalTicker();
        }
    }
    
}
