package com.tickercash.tapereader.tip;

import com.google.inject.Inject;
import com.tickercash.tapereader.ticker.Ticker;

public class DefaultTip implements Tip {
    
    private Ticker ticker;
    
    @Inject
    private DefaultTip(Ticker ticker) {
        this.ticker = ticker;
    }

    @Override
    public void waitForTip() throws Exception {
        ticker.transmit();
    }

}
