package com.tickercash.event.translator;

import org.knowm.xchange.coinmarketcap.dto.marketdata.CoinMarketCapTicker;
import org.knowm.xchange.dto.marketdata.Ticker;

import com.tickercash.event.MarketDataEvent;
import com.tickercash.marketdata.FakeTick;
import com.tickercash.marketdata.Tick;
import com.tickercash.util.UniqueCurrentTimeMS;

public class MarketDataTranslator {
    
    public static final void translateTo(MarketDataEvent event, long sequence, CoinMarketCapTicker ticker) {
        event.set(new Tick(ticker.getName(),"CMC", UniqueCurrentTimeMS.uniqueCurrentTimeMS(), ticker.getPriceBTC().doubleValue()));
    }
    
    public static final void translateTo(MarketDataEvent event, long sequence, String feed, Ticker ticker) {
        event.set(new Tick(ticker.getCurrencyPair().toString(), feed, ticker.getTimestamp().getTime(), ticker.getLast().doubleValue()));
    }
    
    public static final void translateToFakeTick(MarketDataEvent event, long sequence){
        event.set(new FakeTick());
    }

}
