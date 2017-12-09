package com.tickercash.tapereader.event.handler;

import java.util.Properties;

import com.tickercash.tapereader.event.Tick;

public interface EventHandler {
    void onTick(Tick tick,long sequence, boolean endOfBatch) throws Exception;
    
    void configure(Properties prop);
}
