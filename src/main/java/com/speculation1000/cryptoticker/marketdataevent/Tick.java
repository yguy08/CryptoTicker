package com.speculation1000.cryptoticker.marketdataevent;

public class Tick {
	private String symbol;
	private long timestamp;
	private double last;
	private double bid;
	private double ask;
	private double volume;
	
	public Tick set(String symbol, long timestamp, double last, double bid, double ask, int volume) {
		setSymbol(symbol);
		setTimestamp(timestamp);
		setLast(last);
		setBid(bid);
		setAsk(ask);
		setVolume(volume);
		return this;
	}
	
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public void setLast(double last) {
		this.last = last;
	}

	public void setBid(double bid) {
		this.bid = bid;
	}

	public void setAsk(double ask) {
		this.ask = ask;
	}

	public void setVolume(double volume) {
		this.volume = volume;
	}
	
	@Override
	public String toString() {
		return this.symbol+","+this.timestamp+","+this.last+","+this.bid+","+this.ask+","+this.volume;
	}
}
