package com.speculation1000.cryptoticker.tape;

import java.util.List;
import java.util.Properties;

import com.lmax.disruptor.EventTranslatorThreeArg;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.speculation1000.cryptoticker.event.Tick;
import com.speculation1000.cryptoticker.event.handler.TickEventHandler;

public abstract class Tape {
	
	public Properties config;
	
	public final int bufferSize = 1024;
	
	public Disruptor<Tick> disruptor;
	
	public RingBuffer<Tick> ringBuffer;
	
	public List<TickEventHandler> tickEvents;
	
	public abstract void configure(String path) throws Exception;
	
	public abstract void start() throws Exception;
	
    public static final EventTranslatorThreeArg<Tick, String, Long, double[]> TRANSLATOR =
            new EventTranslatorThreeArg<Tick, String, Long, double[]>() {
                public void translateTo(Tick event, long sequence, String symbol, Long timestamp, double[] tickArr){
                    event.set(symbol,timestamp,tickArr[0],tickArr[1],tickArr[2], (int) tickArr[3]);
                }
            };

    public void onData(String symbol, long timestamp, double[] tickArr){
        ringBuffer.publishEvent(TRANSLATOR, symbol, timestamp, tickArr);
    }

}
