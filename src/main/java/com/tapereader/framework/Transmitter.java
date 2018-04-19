package com.tapereader.framework;

import com.tapereader.model.Tick;

public interface Transmitter {
    
    void transmit(Tick tick) throws Exception;
    
    void endTransmission() throws Exception;
    
}
