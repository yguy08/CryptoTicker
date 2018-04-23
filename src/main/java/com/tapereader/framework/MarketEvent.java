package com.tapereader.framework;

import net.openhft.chronicle.wire.AbstractMarshallable;

public class MarketEvent extends AbstractMarshallable {

	private Object event;
	
	public void set(Object value){
		event = value;
	}
	
	public Object get(){
		return event;
	}

}
