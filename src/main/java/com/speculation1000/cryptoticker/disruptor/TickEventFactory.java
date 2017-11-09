package com.speculation1000.cryptoticker.disruptor;

import com.lmax.disruptor.EventFactory;
import com.speculation1000.cryptoticker.marketdataevent.Tick;

public class TickEventFactory implements EventFactory<Tick> {
    @Override
	public Tick newInstance(){
        return new Tick();
    }
}
