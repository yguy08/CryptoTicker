package com.speculation1000.cryptoticker.elements;

public enum TickerType {
	
	//POLONIEX
	POL("POL"),
	//GDAX
	GDX("GDX");
	//...more
	
	private String dataFeedType;
	
	TickerType(String dataFeedType){
		this.dataFeedType = dataFeedType;
	}
	
	public String getDataFeedType(){
		return dataFeedType;
	}
}
