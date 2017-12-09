package com.tickercash.tapereader.tape;

import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;

import com.tickercash.tapereader.core.TickerFunction;
import com.tickercash.tapereader.core.UniqueCurrentTimeMS;
import com.tickercash.tapereader.event.handler.EventHandler;

import net.openhft.chronicle.bytes.Bytes;

public class XchangeLiveTape extends Tape {

    private static final Logger LOGGER = LogManager.getLogger("XchangeLiveTape");
    
    public Exchange EXCHANGE = null;
    
    private Ticker ticker = null;
    
    private Bytes<?> bytes = Bytes.elasticByteBuffer();
    
    @Override
    public void start() throws Exception {
        disruptor.start();
        while(true) {
            for(String symbol : symbols){
	            try{
	            	ticker = EXCHANGE.getMarketDataService().getTicker(new CurrencyPair(symbol));
	            	onTick(bytes
							.append(symbol).append(' ')
							.append(UniqueCurrentTimeMS.uniqueCurrentTimeMS()).append(' ')
							.append(ticker.getLast()).append(' ')
							.append(ticker.getBid()).append(' ')
							.append(ticker.getAsk()).append(' ')
							.append(ticker.getVolume()).append(' '));
	                Thread.sleep(1000);
            	}catch(Exception e){
            		LOGGER.error(e);
            		Thread.sleep(10000);
            	}
          }
	   }
    }

	@Override
	public void configure(Properties props) throws Exception {
        
        setExchange(props.getProperty("xchange.exchange"));
        
        String[] s = props.getProperty("symbols").split(",");
        for(int i = 0; i < s.length;i++){
        	subscribe(s[i]);
        }
        
        for(EventHandler eh : tickEvents){
            eh.configure(props);	
        }
        
	};
	
    private void setExchange(String exchange) {
		EXCHANGE = TickerFunction.XCHANGEFUNC.apply(exchange);
	}
	
}
