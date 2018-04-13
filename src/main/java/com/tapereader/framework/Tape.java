package com.tapereader.framework;

import com.lmax.disruptor.EventHandler;
import com.tapereader.model.Tick;

public interface Tape extends EventHandler<Tick> {
    void write(Tick event) throws Exception;
    void read(Object object) throws Exception;
}
