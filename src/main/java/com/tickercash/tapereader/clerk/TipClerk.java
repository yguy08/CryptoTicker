package com.tickercash.tapereader.clerk;

import com.espertech.esper.client.EventBean;
import com.tickercash.tapereader.tip.TipEngine;
import com.tickercash.tapereader.tip.TipListener;

public class TipClerk implements TipListener {
    
    public TipClerk(TipEngine tip){
        tip.addListener(this::onTick);
    }

    @Override
    public void onTick(EventBean[] newTick, EventBean[] oldData) {
        String symbol = (String) newTick[0].get("symbol");
        String feed = (String) newTick[0].get("feed");
        long timestamp = (Long) newTick[0].get("timestamp");
        double last = (Double) newTick[0].get("last");
        System.out.println(String.format("Symbol: %s, Feed: %s, Timestamp: %d, Last: %f", symbol, feed, timestamp, last));
    }
}
