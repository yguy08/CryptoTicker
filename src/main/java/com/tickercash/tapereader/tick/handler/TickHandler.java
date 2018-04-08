package com.tickercash.tapereader.tick.handler;

import com.tickercash.tapereader.tick.Tick;

public interface TickHandler {
    public void onTick(Tick event, long sequence, boolean endOfBatch) throws Exception;
}
