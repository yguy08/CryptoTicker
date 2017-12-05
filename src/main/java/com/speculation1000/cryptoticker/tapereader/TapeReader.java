package com.speculation1000.cryptoticker.tapereader;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.IntFunction;

import com.speculation1000.cryptoticker.core.UniqueCurrentTimeMS;
import com.speculation1000.cryptoticker.event.Tick;
import com.speculation1000.cryptoticker.event.handler.EventEnum;
import com.speculation1000.cryptoticker.event.handler.EventHandler;
import com.speculation1000.cryptoticker.tape.Tape;
import com.speculation1000.cryptoticker.ticker.Ticker;

public interface TapeReader {

	TapeReader setTicker(Ticker ticker);
    
    TapeReader setTape(Tape tape);
    
    TapeReader subscribe(String symbol);
    
    TapeReader addEvent(EventHandler handler);
    
    TapeReader configure(String path) throws Exception;

    void readTheTape() throws Exception;
    
    public static final IntFunction<String> SYMBOL_FACTORY = 
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
    
    public static final Function<EventEnum,EventHandler> EVENTFACTORY =
            new Function<EventEnum,EventHandler>() {
    
            @Override
            public EventHandler apply(EventEnum t){
                return EventEnum.getHandler(t);
            }
    };
    
    public static final BiFunction<String,org.knowm.xchange.dto.marketdata.Ticker,Tick> TICK_FACTORY =
            new BiFunction<String,org.knowm.xchange.dto.marketdata.Ticker, Tick>() {
            
            @Override
            public Tick apply(String symbol, org.knowm.xchange.dto.marketdata.Ticker value) {
                return new Tick().set(symbol, UniqueCurrentTimeMS.uniqueCurrentTimeMS(),
                        value.getLast().doubleValue(), value.getBid().doubleValue(),
                        value.getAsk().doubleValue(), value.getVolume().intValue());
            }
    };
}
