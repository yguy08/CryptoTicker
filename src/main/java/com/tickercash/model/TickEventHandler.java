package com.tickercash.model;

public interface TickEventHandler {
	
	public void onTick(Tick tick, long sequence, boolean endOfBatch);

}
