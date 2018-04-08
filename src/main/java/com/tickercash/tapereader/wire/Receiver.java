package com.tickercash.tapereader.wire;

import com.lmax.disruptor.EventHandler;
import com.tickercash.tapereader.tick.Tick;

public interface Receiver {
    void startReceiving();
    
    void setEventHandler(EventHandler<Tick> tick);
}
