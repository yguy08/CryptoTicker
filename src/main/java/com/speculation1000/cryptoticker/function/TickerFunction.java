package com.speculation1000.cryptoticker.function;

import java.util.function.Function;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.bittrex.BittrexExchange;
import org.knowm.xchange.poloniex.PoloniexExchange;

public class TickerFunction {

	public static final Function<String,Exchange> EXCHANGEFACTORY = 
            new Function<String,Exchange>() {

            @Override
            public Exchange apply(String t){
                switch(t){
                case "poloniex":
                    return ExchangeFactory.INSTANCE.createExchange(PoloniexExchange.class.getName());
                case "trex":
                	return ExchangeFactory.INSTANCE.createExchange(BittrexExchange.class.getName());
                default:
                	return ExchangeFactory.INSTANCE.createExchange(PoloniexExchange.class.getName());
                }
            }
    };
	
	
}
