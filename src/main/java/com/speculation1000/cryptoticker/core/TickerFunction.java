package com.speculation1000.cryptoticker.core;

import java.util.function.Function;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.bittrex.BittrexExchange;
import org.knowm.xchange.poloniex.PoloniexExchange;

import com.speculation1000.cryptoticker.event.handler.Counter;
import com.speculation1000.cryptoticker.event.handler.EventHandler;
import com.speculation1000.cryptoticker.event.handler.Log;
import com.speculation1000.cryptoticker.event.handler.Save2File;
import com.speculation1000.cryptoticker.tape.CsvTape;
import com.speculation1000.cryptoticker.tape.FakeTape;
import com.speculation1000.cryptoticker.tape.GDAXStreamTape;
import com.speculation1000.cryptoticker.tape.Tape;
import com.speculation1000.cryptoticker.tape.XchangeLiveTape;

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
                case "GDAX-STREAM":
                	return null;
                default:
                	return ExchangeFactory.INSTANCE.createExchange(PoloniexExchange.class.getName());
                }
            }
    };
    
	public static final Function<String,Tape> TAPEFACTORY = (String s) -> {
	            switch(s.toUpperCase()){
	            case "XCHANGE":
	                return new XchangeLiveTape();
	            case "CSV":
	                return new CsvTape();
	            case "FAKE":
	            	return new FakeTape();
                case "GDAX-STREAM":
                	return new GDAXStreamTape();
	            default:
	                return new CsvTape();
	        	}
	        };

	public static final Function<String,EventHandler> EVENTFACTORY = (String s) -> {
			switch(s) {
			case "LOG":
				return new Log();
			case "SAVE":
				return new Save2File();
			case "COUNT":
				return new Counter();
			default:
				return new Log();
			}
	};
	
}
