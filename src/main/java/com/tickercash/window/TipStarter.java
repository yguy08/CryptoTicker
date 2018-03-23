package com.tickercash.window;

import com.tickercash.clerk.Receiver;
import com.tickercash.clerk.TipClerk;
import com.tickercash.marketdata.Tick;
import com.tickercash.tip.TipEngine;
import com.tickercash.tip.TipEngineImpl;

public class TipStarter {
    
    public static void main(String[] args) throws Exception {
        TipEngine tip = new TipEngineImpl(Tick.class, "select symbol, feed, timestamp, last from Tick where symbol = 'BTC/USD'");
        new Receiver("GLOBAL", tip);
        new TipClerk(tip);
    }

}
