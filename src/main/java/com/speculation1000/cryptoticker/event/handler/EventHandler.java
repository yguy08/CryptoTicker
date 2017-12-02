package com.speculation1000.cryptoticker.event.handler;

import com.speculation1000.cryptoticker.event.Tick;

public interface EventHandler {
    void onTick(Tick tick,long sequence, boolean endOfBatch) throws Exception;
    
    //onOrder...
}
