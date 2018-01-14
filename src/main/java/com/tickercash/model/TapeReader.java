package com.tickercash.model;

public class TapeReader {
	
	private QuoteBoy quoteBoy;
	private Ticker ticker;
	private NewsPaper paper;
	private Tip tip;
	
	public QuoteBoy getQuoteBoy() {
		return quoteBoy;
	}
	
	public void setQuoteBoy(QuoteBoy quoteBoy) {
		this.quoteBoy = quoteBoy;
	}
	
	public Ticker getTicker() {
		return ticker;
	}
	
	public void setTicker(Ticker ticker) {
		this.ticker = ticker;
	}
	
	public NewsPaper getPaper() {
		return paper;
	}
	
	public void setPaper(NewsPaper paper) {
		this.paper = paper;
	}
	
	public Tip getTip() {
		return tip;
	}
	
	public void setTip(Tip tip) {
		this.tip = tip;
	}

}
