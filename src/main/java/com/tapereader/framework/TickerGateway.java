package com.tapereader.framework;

import com.tapereader.model.Tick;

public interface TickerGateway {
    
    void transmit(Tick tick) throws Exception;
    
    void endTransmission() throws Exception;
    
}
