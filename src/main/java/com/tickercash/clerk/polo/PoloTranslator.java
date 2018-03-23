package com.tickercash.clerk.polo;

import org.knowm.xchange.poloniex.dto.marketdata.PoloniexMarketData;

import com.tickercash.enums.QuoteBoyType;
import com.tickercash.marketdata.Tick;
import com.tickercash.util.UniqueCurrentTimeMS;

public class PoloTranslator {
    
    public static final void translateTo(Tick event, long sequence, String symbol, PoloniexMarketData chartData) {
        event.set(symbol, QuoteBoyType.POLONIEX.toString(), UniqueCurrentTimeMS.uniqueCurrentTimeMS(), 
                chartData.getLast().doubleValue(), chartData.getBaseVolume().intValue());
    }

}
