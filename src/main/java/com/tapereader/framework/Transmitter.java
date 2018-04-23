package com.tapereader.framework;

public interface Transmitter {
    
    void transmit(String tick) throws Exception;
    
    void endTransmission() throws Exception;
    
}
