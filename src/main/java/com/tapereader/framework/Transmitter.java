package com.tapereader.framework;

import com.lmax.disruptor.EventHandler;
import com.tapereader.model.Tick;

public interface Transmitter extends EventHandler<Tick>{

}
