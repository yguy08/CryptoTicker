package com.tickercash.tapereader.clerk;

import java.time.LocalDateTime;
import java.util.List;

import com.tickercash.tapereader.model.Tick;

public interface HistoricalDataClerk {
    
    public List<Tick> getHistoricalTicks(String currency, LocalDateTime startDate, LocalDateTime endDate) throws Exception;
    
}
