package com.tickercash.tapereader.window;

import com.tickercash.tapereader.clerk.TipClerk;
import com.tickercash.tapereader.model.Tick;
import com.tickercash.tapereader.tip.TipEngine;
import com.tickercash.tapereader.tip.TipEngineImpl;
import com.tickercash.tapereader.wire.TipReceiver;

public class TipStarter {
    
    public static void main(String[] args) throws Exception {
        TipEngine tip = new TipEngineImpl(Tick.class, "select symbol, feed, timestamp, last from Tick");
        new TipReceiver(new TipClerk(), tip).start();
    }

}
