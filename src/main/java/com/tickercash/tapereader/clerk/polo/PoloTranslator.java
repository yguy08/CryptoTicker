package com.tickercash.tapereader.clerk.polo;

import org.knowm.xchange.poloniex.dto.marketdata.PoloniexMarketData;

import com.tickercash.tapereader.clerk.QuoteBoyType;
import com.tickercash.tapereader.model.Tick;
import com.tickercash.tapereader.util.UniqueCurrentTimeMS;

public class PoloTranslator {
    
    public static final void translateTo(Tick event, long sequence, String symbol, PoloniexMarketData chartData) {
        event.set(symbol, QuoteBoyType.POLONIEX.toString(), UniqueCurrentTimeMS.uniqueCurrentTimeMS(), 
                chartData.getLast().doubleValue(), chartData.getBaseVolume().intValue());
    }

}