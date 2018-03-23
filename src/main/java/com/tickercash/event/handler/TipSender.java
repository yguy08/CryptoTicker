package com.tickercash.event.handler;

import com.lmax.disruptor.EventHandler;
import com.tickercash.marketdata.Tick;
import com.tickercash.tip.TipEngine;

public class TipSender implements EventHandler<Tick> {
    
    private TipEngine tip;
    
    public TipSender(TipEngine tip){
        this.tip = tip;
    }

    @Override
    public void onEvent(Tick event, long sequence, boolean endOfBatch) throws Exception {
        tip.sendNewTick(event);
    }
}
