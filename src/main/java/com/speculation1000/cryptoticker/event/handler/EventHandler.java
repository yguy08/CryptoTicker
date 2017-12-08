package com.speculation1000.cryptoticker.event.handler;

import java.util.Properties;

import com.speculation1000.cryptoticker.event.Tick;

public interface EventHandler {
    void onTick(Tick tick,long sequence, boolean endOfBatch) throws Exception;
    
    void configure(Properties prop);
}
