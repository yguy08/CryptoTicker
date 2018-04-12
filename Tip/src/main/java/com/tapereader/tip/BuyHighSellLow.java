package com.tapereader.tip;

import com.google.inject.Inject;
import com.tickercash.tapereader.TapeReader;
import com.tickercash.tapereader.framework.Ticker;

public class BuyHighSellLow extends TapeReader {
    
    @Inject
    protected BuyHighSellLow(Ticker ticker) {
        super(ticker);
    }

}
