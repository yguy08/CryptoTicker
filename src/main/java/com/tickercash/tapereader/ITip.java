package com.tickercash.tapereader;

import com.espertech.esper.client.UpdateListener;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.LifecycleAware;
import com.tickercash.model.Tick;

public interface ITip extends EventHandler<Tick>, LifecycleAware, UpdateListener {

}
