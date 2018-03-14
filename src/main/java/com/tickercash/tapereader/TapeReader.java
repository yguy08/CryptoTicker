package com.tickercash.tapereader;

import com.espertech.esper.client.EventBean;
import com.tickercash.clerk.LiveDataClerk;
import com.tickercash.marketdata.Tick;
import com.tickercash.tip.Tip;

public class TapeReader {
    
    protected LiveDataClerk quoteBoy;
    
    protected Tip tip;
    
    public LiveDataClerk getQuoteBoy() {
    	return quoteBoy;
    }
    
    public void setQuoteBoy(LiveDataClerk quoteBoy) {
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
