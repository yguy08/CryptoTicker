package com.tickercash.window;

import com.tickercash.tapereader.clerk.TipClerk;
import com.tickercash.tapereader.event.Receiver;
import com.tickercash.tapereader.event.Tick;
import com.tickercash.tapereader.tip.TipEngine;
import com.tickercash.tapereader.tip.TipEngineImpl;

public class TipStarter {
    
    public static void main(String[] args) throws Exception {
        TipEngine tip = new TipEngineImpl(Tick.class, "select symbol, feed, timestamp, last from Tick where symbol = 'BTC/USD'");
        new Receiver("GLOBAL", tip);
        new TipClerk(tip);
    }

}
