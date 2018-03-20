package com.tickercash.marketdata;

public class MarketEvent {
	
	private MarketEvent event;
	
	public MarketEvent set(MarketEvent e){
		this.event = e;
		return this;
	}
	
	public MarketEvent get(){
		return event;
	}
	
	public String toString(){
		return event.toString();
	}

}
