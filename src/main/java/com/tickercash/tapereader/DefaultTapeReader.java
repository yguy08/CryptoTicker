package com.tickercash.tapereader;

import com.google.inject.Inject;
import com.tickercash.tapereader.tip.Tip;

public class DefaultTapeReader implements TapeReader {

    private Tip tip;
    
    @Inject
    private DefaultTapeReader(Tip tip){
        this.tip = tip;
    }

    @Override
    public void analyzeTip() throws Exception {
        tip.waitForTip();
    }
}