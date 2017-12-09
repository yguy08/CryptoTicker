package com.tickercash.tapereader.tape;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Executors;

import static net.openhft.chronicle.bytes.StopCharTesters.SPACE_STOP;

import com.lmax.disruptor.EventTranslatorOneArg;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.tickercash.tapereader.event.Tick;
import com.tickercash.tapereader.event.handler.EventHandler;

import net.openhft.chronicle.bytes.Bytes;

public abstract class Tape {

	public static final int BUFFER = 1024;
	
	public Disruptor<Tick> disruptor;
	
	public RingBuffer<Tick> ringBuffer;
	
	public List<EventHandler> tickEvents;
	
	public List<String> symbols;
	
	public abstract void start() throws Exception;
	
	public void subscribe(String symbol) {
		symbols.add(symbol);
	}
	
    @SuppressWarnings("unchecked")
	public void addEventHandler(EventHandler handler){
		disruptor.handleEventsWith(handler::onTick);
		tickEvents.add(handler);
    }
    
    public abstract void configure(Properties cfg) throws Exception;
    
    public Tape(){
    	disruptor = new Disruptor<>(Tick::new, BUFFER, Executors.defaultThreadFactory());
    	tickEvents = new ArrayList<>();
    	symbols = new ArrayList<>();
    	ringBuffer = disruptor.getRingBuffer();
    }
            
	private static final EventTranslatorOneArg<Tick,Bytes<?>> BYTESTRANSLATOR =
            new EventTranslatorOneArg<Tick,Bytes<?>>() {
                public void translateTo(Tick event, long sequence,Bytes<?> bytes){
                    event.set(bytes.parseUtf8(SPACE_STOP),bytes.parseLong(),bytes.parseDouble(),
                    		bytes.parseDouble(),bytes.parseDouble(),(int) bytes.parseDouble());
                }
            };
   
    void onTick(Bytes<?> bytes){
       ringBuffer.publishEvent(BYTESTRANSLATOR, bytes);
    }

}
