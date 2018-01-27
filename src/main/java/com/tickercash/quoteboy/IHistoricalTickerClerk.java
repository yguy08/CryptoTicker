package com.tickercash.quoteboy;

import java.util.List;

import com.tickercash.marketdata.Tick;

public interface IHistoricalTickerClerk {
    
    public List<Tick> getHistoricalTicks(String symbol);

}
