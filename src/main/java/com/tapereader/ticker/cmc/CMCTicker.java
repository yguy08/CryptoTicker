package com.tapereader.ticker.cmc;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.coinmarketcap.CoinMarketCapExchange;
import org.knowm.xchange.coinmarketcap.dto.marketdata.CoinMarketCapTicker;
import org.knowm.xchange.coinmarketcap.service.CoinMarketCapMarketDataService;

import com.tapereader.framework.Engine;
import com.tapereader.framework.Tick;
import com.tapereader.ticker.AbstractTicker;
import com.tapereader.ticker.TickerType;
import com.tapereader.util.UniqueCurrentTimeMS;

public class CMCTicker extends AbstractTicker {
        
    private final Exchange CMC_EXCHANGE;
    
    private final CoinMarketCapMarketDataService CMC_MARKET_DATA_SERVICE;
    
    private static final Logger LOGGER = LogManager.getLogger("CMCQuoteBoy");
    
    public CMCTicker(Engine tape) {
        super(tape);
        CMC_EXCHANGE = ExchangeFactory.INSTANCE.createExchange(CoinMarketCapExchange.class.getName());
        CMC_MARKET_DATA_SERVICE = (CoinMarketCapMarketDataService) CMC_EXCHANGE.getMarketDataService();
    }

    @Override
    public void start() throws Exception {
        disruptor.start();
        running.set(true);
        while(running.get()) {
            try {
                CMC_MARKET_DATA_SERVICE.getCoinMarketCapTickers(100).stream().forEach((s) 
                        -> ringBuffer.publishEvent(this::translateTo, s));
                Thread.sleep(1000);
            } catch (Exception e) {
                LOGGER.error(e);
                Thread.sleep(1000);
            }
        }
    }
    
    private final void translateTo(Tick event, long sequence, CoinMarketCapTicker ticker) {
        event.set(ticker.getIsoCode()+"/BTC", TickerType.CMC.toString(), 
                UniqueCurrentTimeMS.uniqueCurrentTimeMS(), ticker.getPriceBTC().doubleValue());
    }

}