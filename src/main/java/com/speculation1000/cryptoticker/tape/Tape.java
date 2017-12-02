package com.speculation1000.cryptoticker.tape;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Executors;
import org.knowm.xchange.Exchange;
import com.lmax.disruptor.EventTranslatorOneArg;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.speculation1000.cryptoticker.event.Tick;
import com.speculation1000.cryptoticker.event.handler.TickEventHandler;

public abstract class Tape {
	
	public Properties config;
	
	public static final int BUFFER = 1024;
	
	public Disruptor<Tick> disruptor;
	
	public RingBuffer<Tick> ringBuffer;
	
	public List<TickEventHandler> tickEvents;
	
	public List<String> symbols;
	
	public static Exchange EXCHANGE = null;
	
	public abstract void start() throws Exception;
	
	public abstract Tape subscribe(String symbol);
	
    public abstract Tape addTickEventHandler(TickEventHandler handler);
    
    public void setExchange(Exchange exchange){
    	EXCHANGE = exchange;
    };
    
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
	
    public static final EventTranslatorOneArg<Tick,String> TRANSLATOR1 =
        new EventTranslatorOneArg<Tick,String>() {
            public void translateTo(Tick event, long sequence,String tick){
                event.set(tick);
        }
    };
            
    public void onData(Tick tick){
        ringBuffer.publishEvent(TRANSLATOR, tick);
    }
    
    public void onData(String tick){
        ringBuffer.publishEvent(TRANSLATOR1, tick);
    }

}
