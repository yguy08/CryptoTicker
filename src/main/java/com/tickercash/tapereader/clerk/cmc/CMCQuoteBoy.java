package com.tickercash.tapereader.clerk.cmc;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.coinmarketcap.CoinMarketCapExchange;
import org.knowm.xchange.coinmarketcap.dto.marketdata.CoinMarketCapTicker;
import org.knowm.xchange.coinmarketcap.service.CoinMarketCapMarketDataService;

import com.tickercash.tapereader.clerk.QuoteBoy;
import com.tickercash.tapereader.clerk.QuoteBoyType;
import com.tickercash.tapereader.marketdata.Tick;
import com.tickercash.tapereader.util.UniqueCurrentTimeMS;

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
        running.set(true);
        while(running.get()) {
            try {
                CMC_MARKET_DATA_SERVICE.getCoinMarketCapTickers(100).stream().forEach((s) 
                        -> ringBuffer.publishEvent(this::translateTo, s));
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
    
    private final void translateTo(Tick event, long sequence, CoinMarketCapTicker ticker) {
        event.set(ticker.getID().toUpperCase()+"/BTC", getTopicName(), UniqueCurrentTimeMS.uniqueCurrentTimeMS(), ticker.getPriceBTC().doubleValue());
    }

}