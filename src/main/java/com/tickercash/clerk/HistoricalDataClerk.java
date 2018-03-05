package com.tickercash.clerk;

import java.time.LocalDateTime;
import java.util.List;

import com.tickercash.marketdata.Tick;

public interface HistoricalDataClerk {
    
    public List<Tick> getHistoricalTicks(String currency, LocalDateTime startDate, LocalDateTime endDate) throws Exception;
    
}
