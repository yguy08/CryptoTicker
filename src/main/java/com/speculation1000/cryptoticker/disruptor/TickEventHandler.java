package com.speculation1000.cryptoticker.disruptor;

import com.lmax.disruptor.EventHandler;
import com.speculation1000.cryptoticker.marketdataevent.Tick;

public class TickEventHandler implements EventHandler<Tick>{
    @Override
	public void onEvent(Tick event, long sequence, boolean endOfBatch){
        System.out.println(event);
    }
}
