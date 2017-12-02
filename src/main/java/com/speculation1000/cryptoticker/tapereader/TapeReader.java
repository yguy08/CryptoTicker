package com.speculation1000.cryptoticker.tapereader;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.IntFunction;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.poloniex.PoloniexExchange;

import com.speculation1000.cryptoticker.core.UniqueCurrentTimeMS;
import com.speculation1000.cryptoticker.event.Tick;
import com.speculation1000.cryptoticker.event.handler.Save2File;
import com.speculation1000.cryptoticker.event.handler.TickEventHandler;
import com.speculation1000.cryptoticker.event.handler.TickEventHandlerImpl;
import com.speculation1000.cryptoticker.tape.CsvTape;
import com.speculation1000.cryptoticker.tape.FakeTape;
import com.speculation1000.cryptoticker.tape.Tape;
import com.speculation1000.cryptoticker.tape.XchangeLiveTape;
import com.speculation1000.cryptoticker.ticker.HistoricalTicker;
import com.speculation1000.cryptoticker.ticker.LiveTicker;
import com.speculation1000.cryptoticker.ticker.Ticker;

public interface TapeReader {

	TapeReader setTicker(Ticker ticker);
    
    TapeReader setTape(Tape tape);
    
    TapeReader subscribe(String symbol);
    
    TapeReader addTickEvent(TickEventHandler handler);

	TapeReader setExchange(Exchange apply);
    
    //set what ever is in reader test...

    void readTheTape() throws Exception;

    void configure(String cfg) throws Exception;

    public static final Function<String,Ticker> TICKER_FACTORY =
            new Function<String,Ticker>() {

                @Override
                public Ticker apply(String t) {
                    switch(t){
                    case "live":
                        return new LiveTicker();
                    case "historical":
                        return new HistoricalTicker();
                    default:
                        return new HistoricalTicker();
                    }
                }
    };

    public static final Function<String,Tape> TAPE_FACTORY = 
            new Function<String,Tape>() {

            @Override
            public Tape apply(String t){
                switch(t){
                case "xchange":
                    return new XchangeLiveTape();
                case "csv":
                    return new CsvTape();
                case "fake":
                	return new FakeTape();
                default:
                    return new CsvTape();
            	}
            }
    };
    
    public static final Function<String,Exchange> EXCHANGE_FACTORY = 
            new Function<String,Exchange>() {

            @Override
            public Exchange apply(String t){
                switch(t){
                case "poloniex":
                    return ExchangeFactory.INSTANCE.createExchange(PoloniexExchange.class.getName());
                default:
                	return ExchangeFactory.INSTANCE.createExchange(PoloniexExchange.class.getName());
                }
            }
    };
    
    public static final IntFunction<String> SYMBOL_FACTORY = 
            new IntFunction<String>() {

            @Override
            public String apply(int t){
                switch(t){
                case 1:
                    return "BTC/USDT"; //properties
                default:
                	return "BTC/USDT";
                }
            }
    };
    
    public static final IntFunction<TickEventHandler> EVENT_FACTORY =
            new IntFunction<TickEventHandler>() {
    
            @Override
            public TickEventHandler apply(int t){
                switch(t){
                case 1:
                    return new TickEventHandlerImpl();
                case 2:
                	return new Save2File();
                default:
                    return new TickEventHandlerImpl();
                }
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
