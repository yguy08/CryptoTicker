package com.tickercash.clerk.cmc;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.coinmarketcap.CoinMarketCapExchange;
import org.knowm.xchange.coinmarketcap.dto.marketdata.CoinMarketCapTicker;
import org.knowm.xchange.coinmarketcap.service.CoinMarketCapMarketDataService;

import com.lmax.disruptor.EventTranslatorOneArg;
import com.tickercash.clerk.LiveDataClerk;
import com.tickercash.marketdata.CMCTicker;
import com.tickercash.marketdata.MarketEvent;
import com.tickercash.marketdata.Tick;
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
						-> ringBuffer.publishEvent(TRANSLATOR_CMC_UNFILTERED::translateTo,s));
				Thread.sleep(throttle);
			} catch (Exception e) {
				TapeLogger.getLogger().error(e);
				Thread.sleep(throttle);
			}
		}		
	}
	
	public static final EventTranslatorOneArg<MarketEvent,CoinMarketCapTicker> TRANSLATOR_CMC_UNFILTERED
	= (MarketEvent event, long sequence, CoinMarketCapTicker cmcTicker) -> event.set(new CMCTicker(cmcTicker));

}
