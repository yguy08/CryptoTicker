package com.speculation1000.cryptoticker.model;

import org.knowm.xchange.dto.marketdata.Ticker;

import com.speculation1000.cryptoticker.core.UniqueCurrentTimeMS;

public class TickEventTranslator {
	
    public static void translate(Tick event, long sequence,Ticker ticker){
        event.set(ticker.getCurrencyPair().toString(),UniqueCurrentTimeMS.uniqueCurrentTimeMS(),
        		ticker.getLast().doubleValue(),ticker.getBid().doubleValue(),ticker.getAsk().doubleValue(),
        		ticker.getVolume().intValue());
    }

}
