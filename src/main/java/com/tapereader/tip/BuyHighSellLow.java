package com.tapereader.tip;

import com.google.inject.Inject;
import com.tapereader.TapeReader;
import com.tapereader.framework.Ticker;

public class BuyHighSellLow extends TapeReader {
    
    @Inject
    protected BuyHighSellLow(Ticker ticker) {
        super(ticker);
    }

}
