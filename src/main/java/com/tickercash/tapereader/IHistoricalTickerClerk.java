package com.tickercash.tapereader;

import java.util.List;

public interface IHistoricalTickerClerk {
    
    public List<Tick> getHistoricalTicks(String symbol);

}
