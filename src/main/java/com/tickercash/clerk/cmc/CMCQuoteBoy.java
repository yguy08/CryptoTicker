package com.tickercash.clerk.cmc;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.coinmarketcap.CoinMarketCapExchange;
import org.knowm.xchange.coinmarketcap.service.CoinMarketCapMarketDataService;

import com.tickercash.clerk.LiveDataClerk;
import com.tickercash.marketdata.MarketEvent;
import com.tickercash.util.TapeLogger;
import com.tickercash.util.UniqueCurrentTimeMS;

public class CMCQuoteBoy extends LiveDataClerk {
	    
    private final Exchange CMC_EXCHANGE;
    
    private final CoinMarketCapMarketDataService CMC_MARKET_DATA_SERVICE;
    
    private int throttle = 1000;
	
	public CMCQuoteBoy() {
		CMC_EXCHANGE = ExchangeFactory.INSTANCE.createExchange(CoinMarketCapExchange.class.getName());
		CMC_MARKET_DATA_SERVICE = (CoinMarketCapMarketDataService) CMC_EXCHANGE.getMarketDataService();		
	}
	
	public CMCQuoteBoy(int seconds) {
		CMC_EXCHANGE = ExchangeFactory.INSTANCE.createExchange(CoinMarketCapExchange.class.getName());
		CMC_MARKET_DATA_SERVICE = (CoinMarketCapMarketDataService) CMC_EXCHANGE.getMarketDataService();
		throttle = seconds*1000;
	}

	@Override
	public void start() throws Exception {
		disruptor.start();
		while(true) {
			try {
				CMC_MARKET_DATA_SERVICE.getCoinMarketCapTickers(100).stream().forEach((s) 
						-> ringBuffer.publishEvent(MarketEvent.TRANSLATOR_SYMBOL_TS_LAST::translateTo, 
								s.getIsoCode()+"/BTC", UniqueCurrentTimeMS.uniqueCurrentTimeMS(), s.getPriceBTC().doubleValue()));
				Thread.sleep(throttle);
			} catch (Exception e) {
				TapeLogger.getLogger().error(e);
				Thread.sleep(throttle);
			}
		}		
	}

}
