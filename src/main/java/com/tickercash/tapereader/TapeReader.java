package com.tickercash.tapereader;

import com.espertech.esper.client.EventBean;
import com.tickercash.config.TapeReaderConfig;
import com.tickercash.marketdata.Tick;
import com.tickercash.quoteboy.QuoteBoy;
import com.tickercash.tip.Tip;

public class TapeReader {
    
	protected TapeReaderConfig config;
    protected QuoteBoy quoteBoy;
    protected Tip tip;
    
    public TapeReaderConfig getTapeReaderConfig() {
    	return config;
    }
    
    public void setTapeReaderConfig(TapeReaderConfig cfg) {
    	config = cfg;
    }
    
    public QuoteBoy getQuoteBoy() {
    	return quoteBoy;
    }
    
    public void setQuoteBoy(QuoteBoy quoteBoy) {
    	this.quoteBoy = quoteBoy;
    }
    
    public Tip getTip() {
    	return tip;
    }
    
    public void setTip(Tip tip) {
    	this.tip = tip;
    }
    
    public void onTick(Tick tick, long sequence, boolean endOfBatch) {
    	
    }
    
    public void onTip(EventBean[] newEvents, EventBean[] oldEvents) {
    	
    }
    
    public void readTheTape() {
    	
    }
    
    
    
}
