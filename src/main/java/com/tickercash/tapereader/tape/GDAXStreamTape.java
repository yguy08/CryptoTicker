package com.tickercash.tapereader.tape;

import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.knowm.xchange.currency.CurrencyPair;

import com.lmax.disruptor.EventTranslatorOneArg;
import com.tickercash.tapereader.event.Tick;
import com.tickercash.tapereader.event.handler.EventHandler;

import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.core.StreamingExchangeFactory;
import info.bitrich.xchangestream.gdax.GDAXStreamingExchange;
import net.openhft.chronicle.bytes.Bytes;

public class GDAXStreamTape extends Tape {

    private Bytes<?> bytes = Bytes.elasticByteBuffer();
    
    private static final Logger LOG = LogManager.getLogger("GDAXStreamTape");
    
    private StreamingExchange exchange;

    @Override
	public void start() throws Exception {
		disruptor.start();
		exchange = StreamingExchangeFactory.INSTANCE.createExchange(GDAXStreamingExchange.class.getName());
		
		while(true){
			// Connect to the Exchange WebSocket API. Blocking wait for the connection.
			exchange.connect().blockingAwait();
	        
			try{
	            exchange.getStreamingMarketDataService().getTicker(CurrencyPair.BTC_USD).subscribe(ticker -> {
			    onTick(bytes.append(ticker.getTimestamp().getTime()).append(' ')
			            .append(ticker.getLast().doubleValue()).append(' '));
			    }, throwable -> LOG.error("ERROR in getting ticker: ", throwable));
			} catch(Exception e){
				
			}
			
			while(true){
				
			}
		}
		
    }
    
	private static final EventTranslatorOneArg<Tick,Bytes<?>> BYTESTRANSLATOR =
            new EventTranslatorOneArg<Tick,Bytes<?>>() {
                public void translateTo(Tick event, long sequence,Bytes<?> bytes){
                    event.set(bytes.parseLong(),bytes.parseDouble());
                }
            };
    
    @Override
    void onTick(Bytes<?> bytes){
        ringBuffer.publishEvent(BYTESTRANSLATOR, bytes);
     }

	@Override
	public void configure(Properties cfg) throws Exception {
		
        for(EventHandler eh : tickEvents){
            eh.configure(cfg);	
        }
		
	}

}
