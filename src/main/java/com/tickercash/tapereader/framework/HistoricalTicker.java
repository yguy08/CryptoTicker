package com.tickercash.tapereader.framework;

import java.time.LocalDateTime;
import java.util.List;

import com.tickercash.tapereader.model.Tick;
import com.tickercash.tapereader.ticker.CMCHistoricalDataClerk;
import com.tickercash.tapereader.ticker.QuoteBoyType;

public interface HistoricalTicker {
    
    List<Tick> getHistoricalTicks(String currency, LocalDateTime startDate, LocalDateTime endDate) throws Exception;
    
    static HistoricalTicker createHistoricalDataClerk(QuoteBoyType type) {
        switch(type) {
        case CMC:
            return new CMCHistoricalDataClerk();
        case POLONIEX:
        case GDAX:
        case FAKE:
        default:
            return new CMCHistoricalDataClerk();
        }
    }
    
}
