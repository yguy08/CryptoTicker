package com.tickercash.clerk;

import java.util.List;

import com.tickercash.marketdata.Tick;

public interface HistoricalDataClerk {
    
    public List<Tick> getHistoricalTicks(String symbol, long start, long end);
    
    public List<Tick> getHistoricalTicks(String symbol, String start, String end) throws Exception;

}
