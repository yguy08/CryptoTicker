package com.speculation1000.cryptoticker.tape;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.function.Function;

import com.lmax.disruptor.EventTranslatorOneArg;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.speculation1000.cryptoticker.event.Tick;
import com.speculation1000.cryptoticker.event.handler.EventHandler;

public abstract class Tape {
	
	public Properties config;
	
	public static final int BUFFER = 1024;
	
	public Disruptor<Tick> disruptor;
	
	public RingBuffer<Tick> ringBuffer;
	
	public List<EventHandler> tickEvents;
	
	public List<String> symbols;
	
	public abstract void start() throws Exception;
	
	public abstract void subscribe(String symbol);
	
    public abstract void addEventHandler(EventHandler handler);
    
    public abstract void configure(String path) throws Exception;
    
    public Tape(){
    	disruptor = new Disruptor<>(Tick::new, BUFFER, Executors.defaultThreadFactory());
    	tickEvents = new ArrayList<>();
    	symbols = new ArrayList<>();
    	ringBuffer = disruptor.getRingBuffer();
    }
	
	public static final EventTranslatorOneArg<Tick,Tick> TRANSLATOR =
            new EventTranslatorOneArg<Tick,Tick>() {
                public void translateTo(Tick event, long sequence,Tick tick){
                    event.set(tick);
                }
            };
            
    public void onData(Tick tick){
        ringBuffer.publishEvent(TRANSLATOR, tick);
    }
    
    public static final Function<String,Tape> TAPEFACTORY = 
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

}
