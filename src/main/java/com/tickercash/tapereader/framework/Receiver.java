package com.tickercash.tapereader.framework;

import com.lmax.disruptor.EventHandler;
import com.tickercash.tapereader.model.Tick;

public interface Receiver {
    void startReceiving();
    
    void setEventHandler(EventHandler<Tick> tick);
}
