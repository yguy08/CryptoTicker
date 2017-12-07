package com.speculation1000.cryptoticker.core;

import java.util.function.Function;
import java.util.function.IntFunction;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.bittrex.BittrexExchange;
import org.knowm.xchange.poloniex.PoloniexExchange;

import com.speculation1000.cryptoticker.event.handler.EventEnum;
import com.speculation1000.cryptoticker.event.handler.EventHandler;
import com.speculation1000.cryptoticker.tape.CsvTape;
import com.speculation1000.cryptoticker.tape.FakeTape;
import com.speculation1000.cryptoticker.tape.Tape;
import com.speculation1000.cryptoticker.tape.XchangeLiveTape;
import com.speculation1000.cryptoticker.ticker.SimpleTicker;

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
	
}
