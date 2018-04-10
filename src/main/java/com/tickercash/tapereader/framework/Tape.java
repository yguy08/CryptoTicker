package com.tickercash.tapereader.framework;

import com.tickercash.tapereader.model.Tick;
import com.lmax.disruptor.EventHandler;

public interface Tape extends EventHandler<Tick> {
    @Override
    void onEvent(Tick event, long sequence, boolean endOfBatch) throws Exception;
}
