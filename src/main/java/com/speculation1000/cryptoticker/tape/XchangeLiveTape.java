package com.speculation1000.cryptoticker.tape;

import java.io.FileInputStream;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;

import com.speculation1000.cryptoticker.core.TickerFunction;
import com.speculation1000.cryptoticker.core.UniqueCurrentTimeMS;
import com.speculation1000.cryptoticker.event.handler.EventHandler;

import net.openhft.chronicle.bytes.Bytes;

public class XchangeLiveTape extends Tape {

    private static final Logger LOGGER = LogManager.getLogger("XchangeDataFeed");
    
    public Exchange EXCHANGE = null;
    
    private Ticker ticker = null;
    
    private Bytes<?> bytes = Bytes.elasticByteBuffer();
    
    @Override
    public void start() throws Exception {
        disruptor.start();
        while(true) {
            for(String symbol : symbols){
            	ticker = EXCHANGE.getMarketDataService().getTicker(new CurrencyPair(symbol));
            	onTick(bytes
						.append(symbol).append(' ')
						.append(UniqueCurrentTimeMS.uniqueCurrentTimeMS()).append(' ')
						.append(ticker.getLast()).append(' ')
						.append(ticker.getBid()).append(' ')
						.append(ticker.getAsk()).append(' ')
						.append(ticker.getVolume()).append(' '));
                Thread.sleep(5000);
          }
	   }
    }

	@Override
	public void subscribe(String symbol) {
		symbols.add(symbol);
	}

	@Override
	public void addEventHandler(EventHandler handler) {
		disruptor.handleEventsWith(handler::onTick);
	}

	@Override
	public void configure(String path) throws Exception {
        config = new Properties();
        config.load(new FileInputStream(path));
        
        setExchange(config.getProperty("xchange.exchange"));
        
        String[] s = config.getProperty("symbols").split(",");
        for(int i = 0; i < s.length;i++){
        	subscribe(s[i]);
        }
        
	};
	
    private void setExchange(String exchange) {
		EXCHANGE = TickerFunction.XCHANGEFUNC.apply(exchange);
	}
	
}
