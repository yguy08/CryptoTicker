package com.tickercash.tapereader.clerk.cmc;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.coinmarketcap.CoinMarketCapExchange;
import org.knowm.xchange.coinmarketcap.service.CoinMarketCapMarketDataService;

import com.tickercash.tapereader.clerk.QuoteBoy;
import com.tickercash.tapereader.clerk.QuoteBoyType;
import com.tickercash.tapereader.event.MarketDataTranslator;

public class CMCQuoteBoy extends QuoteBoy {
        
    private final Exchange CMC_EXCHANGE;
    
    private final CoinMarketCapMarketDataService CMC_MARKET_DATA_SERVICE;
    
    private static final Logger LOGGER = LogManager.getLogger("CMCQuoteBoy");
    
    public CMCQuoteBoy() {
        CMC_EXCHANGE = ExchangeFactory.INSTANCE.createExchange(CoinMarketCapExchange.class.getName());
        CMC_MARKET_DATA_SERVICE = (CoinMarketCapMarketDataService) CMC_EXCHANGE.getMarketDataService();
        throttle = 5000;
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
                        -> ringBuffer.publishEvent(MarketDataTranslator::translateTo, s));
                Thread.sleep(throttle);
            } catch (Exception e) {
                LOGGER.error(e);
                Thread.sleep(throttle);
            }
        }
    }

    @Override
    public String getTopicName() {
        return QuoteBoyType.CMC.toString();
    }

}