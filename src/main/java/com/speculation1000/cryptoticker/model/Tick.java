package com.speculation1000.cryptoticker.model;

public class Tick {
	private String symbol;
	private long timestamp;
	private double last;
	private double bid;
	private double ask;
	private int volume;
	
	public Tick set(String symbol, long timestamp, double last, double bid, double ask, int volume) {
		setSymbol(symbol);
		setTimestamp(timestamp);
		setLast(last);
		setBid(bid);
		setAsk(ask);
		setVolume(volume);
		return this;
	}
	
	public String getSymbol(){
		return symbol;
	}
	
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	
	public long getTimestamp(){
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	
	public double getLast(){
		return last;
	}

	public void setLast(double last) {
		this.last = last;
	}
	
	public double getBid(){
		return bid;
	}

	public void setBid(double bid) {
		this.bid = bid;
	}
	
	public double getAsk(){
		return ask;
	}

	public void setAsk(double ask) {
		this.ask = ask;
	}
	
	public int getVolume(){
		return volume;
	}

	public void setVolume(int volume) {
		this.volume = volume;
	}
	
	@Override
	public String toString() {
		return this.symbol+","+this.timestamp+","+this.last+","+this.bid+","+this.ask+","+this.volume;
	}
}
