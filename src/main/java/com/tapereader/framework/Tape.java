package com.tapereader.framework;

import com.lmax.disruptor.EventHandler;
import com.tapereader.model.Tick;

public interface Tape extends EventHandler<Tick> {
    @Override
    void onEvent(Tick event, long sequence, boolean endOfBatch) throws Exception;
}
