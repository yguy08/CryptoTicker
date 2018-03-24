package com.tickercash.tapereader.tip;

import com.lmax.disruptor.EventHandler;
import com.tickercash.tapereader.event.Tick;

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
