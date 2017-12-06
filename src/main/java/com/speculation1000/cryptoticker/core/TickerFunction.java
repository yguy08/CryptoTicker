package com.speculation1000.cryptoticker.core;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.IntFunction;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.bittrex.BittrexExchange;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.poloniex.PoloniexExchange;

import com.speculation1000.cryptoticker.event.Tick;
import com.speculation1000.cryptoticker.event.handler.EventEnum;
import com.speculation1000.cryptoticker.event.handler.EventHandler;
import com.speculation1000.cryptoticker.tape.CsvTape;
import com.speculation1000.cryptoticker.tape.FakeTape;
import com.speculation1000.cryptoticker.tape.Tape;
import com.speculation1000.cryptoticker.tape.XchangeLiveTape;
import com.speculation1000.cryptoticker.ticker.SimpleTicker;

import net.openhft.chronicle.bytes.Bytes;

public class TickerFunction {

	public static final Function<String,Exchange> XCHANGEFUNC = 
            new Function<String,Exchange>() {

            @Override
            public Exchange apply(String t){
                switch(t.toUpperCase()){
                case "POLONIEX":
                    return ExchangeFactory.INSTANCE.createExchange(PoloniexExchange.class.getName());
                case "TREX":
                	return ExchangeFactory.INSTANCE.createExchange(BittrexExchange.class.getName());
                default:
                	return ExchangeFactory.INSTANCE.createExchange(PoloniexExchange.class.getName());
                }
            }
    };
    
	public static final Function<String,Tape> TAPEFACTORY = 
	        new Function<String,Tape>() {
	
	        @Override
	        public Tape apply(String t){
	            switch(t.toUpperCase()){
	            case "XCHANGE":
	                return new XchangeLiveTape();
	            case "CSV":
	                return new CsvTape();
	            case "FAKE":
	            	return new FakeTape();
	            default:
	                return new CsvTape();
	        	}
	        }
	};
	
	public static final Function<String,com.speculation1000.cryptoticker.ticker.Ticker> TICKERFUNC = 
	        new Function<String,com.speculation1000.cryptoticker.ticker.Ticker>() {
	
	        @Override
	        public com.speculation1000.cryptoticker.ticker.Ticker apply(String t){
	            switch(t.toUpperCase()){
	            case "SIMPLE":
	                return new SimpleTicker();
	            default:
	                return new SimpleTicker();
	        	}
	        }
	};

	public static final BiFunction<String,org.knowm.xchange.dto.marketdata.Ticker,Tick> TICKFACTORY =
	        new BiFunction<String,org.knowm.xchange.dto.marketdata.Ticker, Tick>() {
	        
	        @Override
	        public Tick apply(String symbol, org.knowm.xchange.dto.marketdata.Ticker value) {
	            return new Tick().set(symbol, UniqueCurrentTimeMS.uniqueCurrentTimeMS(),
	                    value.getLast().doubleValue(), value.getBid().doubleValue(),
	                    value.getAsk().doubleValue(), value.getVolume().intValue());
	        }
	};

	public static final Function<EventEnum,EventHandler> EVENTFACTORY =
	        new Function<EventEnum,EventHandler>() {
	
	        @Override
	        public EventHandler apply(EventEnum t){
	            return EventEnum.getHandler(t);
	        }
	};

	public static final IntFunction<String> SYMBOLFACTORY = 
	        new IntFunction<String>() {
	
	        @Override
	        public String apply(int t){
	            switch(t){
	            case 1:
	                return "BTC/USDT";
	            case 2:
	            	return "ETH/BTC";//properties
	            default:
	            	return "BTC/USDT";
	            }
	        }
	};
	
	/**
	 * An operation that accepts an Xchange Ticker and returns
	 * a <code>Bytes</code> object of tick data.
	 */
	public static final BiFunction<String,org.knowm.xchange.dto.marketdata.Ticker,Bytes<?>> XTICKER2BYTESFUNC =
			new BiFunction<String,org.knowm.xchange.dto.marketdata.Ticker,Bytes<?>> () {
		
				@Override
				public Bytes<?> apply(String symbol, Ticker t) { 
					return Bytes.elasticByteBuffer()
							.append(symbol).append(' ')
							.append(UniqueCurrentTimeMS.uniqueCurrentTimeMS()).append(' ')
							.append(t.getLast()).append(' ')
							.append(t.getBid()).append(' ')
							.append(t.getAsk()).append(' ')
							.append(t.getVolume()).append(' ');
				}
		
	};
	
	/**
	 * An operation that accepts a String of tick data and returns
	 * a <code>Bytes</code> object of tick data.
	 */
	public static final Function<String,Bytes<?>> TICK_STR_2_BYTES_FUNC =
			new Function<String,Bytes<?>> () {
		
				@Override
				public Bytes<?> apply(String tick) {
					return null;
				}
		
	};
	
}
