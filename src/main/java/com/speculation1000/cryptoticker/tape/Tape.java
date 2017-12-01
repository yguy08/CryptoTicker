package com.speculation1000.cryptoticker.tape;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.function.Function;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.poloniex.PoloniexExchange;

import com.lmax.disruptor.EventTranslatorThreeArg;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.speculation1000.cryptoticker.event.Tick;
import com.speculation1000.cryptoticker.event.handler.TickEventHandler;
import com.speculation1000.cryptoticker.event.handler.TickEventHandlerImpl;

public abstract class Tape {
	
	public Properties config;
	
	public static final int BUFFER = 1024;
	
	public Disruptor<Tick> disruptor;
	
	public RingBuffer<Tick> ringBuffer;
	
	public List<TickEventHandler> tickEvents;
	
	public List<String> symbols;
	
	public static Exchange EXCHANGE = null;
	
	public abstract void start() throws Exception;
	
	public abstract void subscribe(String... symbol);
	
    public abstract void addTickEventHandler(TickEventHandler... handler);
    
    public abstract void setExchange(Exchange exchange);
    
    public Tape(){
    	disruptor = new Disruptor<>(Tick::new, BUFFER, Executors.defaultThreadFactory());
    	tickEvents = new ArrayList<>();
    	symbols = new ArrayList<>();
    	ringBuffer = disruptor.getRingBuffer();
    }
	
	public static final EventTranslatorThreeArg<Tick, String, Long, double[]> TRANSLATOR =
            new EventTranslatorThreeArg<Tick, String, Long, double[]>() {
                public void translateTo(Tick event, long sequence, String symbol, Long timestamp, double[] tickArr){
                    event.set(symbol,timestamp,tickArr[0],tickArr[1],tickArr[2], (int) tickArr[3]);
                }
            };
            
    public static final Function<String,Tape> TAPEFACTORY =
            new Function<String,Tape>() {

				@Override
				public Tape apply(String t) {
					switch(t){
					case "CSV":
						return new CsvTape();
					case "xchangelive":
						return new XchangeLiveTape();
					default: 
						return new CsvTape();
					}
				}
            };
            
    public static final Function<String,Disruptor<?>> DISRUPTORFACTORY =
    		new Function<String,Disruptor<?>>() {

				@Override
				public Disruptor<?> apply(String t) {
					switch(t){
					case "Tick":
						return new Disruptor<>(Tick::new, BUFFER, Executors.defaultThreadFactory());
					default:
						return new Disruptor<>(Tick::new, BUFFER, Executors.defaultThreadFactory());
					}
				}
    	
    };
    
    public static final Function<String,TickEventHandler> TICKEVENTFACTORY =
    		new Function<String,TickEventHandler>() {

				@Override
				public TickEventHandler apply(String t) {
					switch(t){
					case "tickevent":
						return new TickEventHandlerImpl();
					default:
						return new TickEventHandlerImpl();
					}
				}
    	
    };
    
    public static final Function<String, Exchange> EXCHANGEFACTORY = 
    		new Function<String, Exchange>() {

				@Override
				public Exchange apply(String t) {
					switch(t){
					case "poloniex":
						return ExchangeFactory.INSTANCE.createExchange(PoloniexExchange.class.getName());
					default:
						return ExchangeFactory.INSTANCE.createExchange(PoloniexExchange.class.getName());
					}
				}
    	
    };
            
    public void onData(String symbol, long timestamp, double[] tickArr){
        ringBuffer.publishEvent(TRANSLATOR, symbol, timestamp, tickArr);
    }

}
