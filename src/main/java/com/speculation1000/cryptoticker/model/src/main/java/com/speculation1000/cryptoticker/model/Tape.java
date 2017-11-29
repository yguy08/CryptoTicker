package com.speculation1000.cryptoticker.model;

import java.util.List;
import java.util.Properties;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;

public abstract class Tape {
	
	protected Properties config;
	
	protected final int bufferSize = 1024;
	
	protected Disruptor<Tick> disruptor;
	
	protected RingBuffer<Tick> ringBuffer;
	
	protected List<TickEvent> tickEvents;
	
	abstract void configure(String path) throws Exception;
	
	abstract void start() throws Exception;

}
