package com.tickercash.tapereader.clerk;

import java.time.LocalDateTime;
import java.util.List;

import com.tickercash.tapereader.clerk.cmc.CMCHistoricalDataClerk;
import com.tickercash.tapereader.tick.Tick;

public interface HistoricalDataClerk {
    
    List<Tick> getHistoricalTicks(String currency, LocalDateTime startDate, LocalDateTime endDate) throws Exception;
    
    static HistoricalDataClerk createHistoricalDataClerk(QuoteBoyType type) {
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
