package com.tickercash.tapereader;

import com.google.inject.Inject;
import com.tickercash.tapereader.framework.Listener;
import com.tickercash.tapereader.model.Tick;
import com.tickercash.tapereader.ticker.Ticker;

public class TapeReader implements Listener {
    
    private Ticker ticker;
    
    @Inject
    private TapeReader(Ticker ticker) {
        this.ticker = ticker;
    }
    
    public void readTheTape() throws Exception {
        ticker.transmit();
    }

    @Override
    public void onEvent(Tick tick) {
        System.out.println(tick);
    }
    
}