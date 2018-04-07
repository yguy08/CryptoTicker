package com.tickercash.tapereader;

import com.google.inject.Inject;
import com.tickercash.tapereader.ticker.Ticker;
import com.tickercash.tapereader.tip.Tip;

public class TapeReader {
    
    private Tip tip;
    
    private Ticker ticker;
    
    @Inject
    protected TapeReader(Tip tip, Ticker ticker){
        this.tip = tip;
        this.ticker = ticker;
    }
    
    public void readTheTape() throws Exception {
        ticker.writeToTape();
    }
    
    public void onTipSatisfied() throws Exception {
        
    }
}