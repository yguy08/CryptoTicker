package com.tickercash.tapereader.tape;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Executors;

import com.lmax.disruptor.EventTranslatorThreeArg;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.tickercash.tapereader.event.Tick;
import com.tickercash.tapereader.event.handler.TickEventHandler;

public abstract class Tape {

	public static final int BUFFER = 1024;
	
	public Disruptor<Tick> disruptor;
	
	public RingBuffer<Tick> ringBuffer;
	
	public List<TickEventHandler> tickEvents;
	
	public List<String> symbols;
	
	public abstract void start() throws Exception;
	
	public void subscribe(String symbol) {
		symbols.add(symbol);
	}
	
    @SuppressWarnings("unchecked")
	public void addEventHandler(TickEventHandler handler) throws Exception {
		disruptor.handleEventsWith(handler::onEvent);
		tickEvents.add(handler);
		handler.configure();
    }
    
    public abstract void configure() throws Exception;
    
    public Tape(){
    	disruptor = new Disruptor<>(Tick::new, BUFFER, Executors.defaultThreadFactory());
    	tickEvents = new ArrayList<>();
    	symbols = new ArrayList<>();
    	ringBuffer = disruptor.getRingBuffer();
    }
            
	private static final EventTranslatorThreeArg<Tick,String,Long,Double> BYTESTRANSLATOR =
            new EventTranslatorThreeArg<Tick,String,Long,Double>() {

				@Override
				public void translateTo(Tick event, long sequence, String symbol, Long timestamp, Double last) {
					event.set(symbol, timestamp, last);
				}
                
            };
   
    public void onTick(String symbol, long timestamp, double last){
       ringBuffer.publishEvent(BYTESTRANSLATOR, symbol, timestamp, last);
    }

}
