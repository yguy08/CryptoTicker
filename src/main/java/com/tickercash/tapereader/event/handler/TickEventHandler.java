package com.tickercash.tapereader.event.handler;

import com.lmax.disruptor.EventHandler;
import com.tickercash.tapereader.event.Tick;

public abstract class TickEventHandler implements EventHandler<Tick> {

	public abstract void configure() throws Exception;

}
