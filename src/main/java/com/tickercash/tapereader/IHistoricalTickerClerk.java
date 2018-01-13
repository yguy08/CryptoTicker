package com.tickercash.tapereader;

import java.util.List;

import com.tickercash.model.Tick;

public interface IHistoricalTickerClerk {
    
    public List<Tick> getHistoricalTicks(String symbol);

}
