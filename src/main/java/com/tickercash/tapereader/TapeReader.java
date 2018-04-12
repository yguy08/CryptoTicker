package com.tickercash.tapereader;

import com.google.inject.Inject;
import com.tickercash.tapereader.framework.Ticker;
import com.tickercash.tapereader.listener.OrderEventListener;
import com.tickercash.tapereader.listener.TickEventListener;
import com.tickercash.tapereader.model.Order;
import com.tickercash.tapereader.model.Tick;

public class TapeReader implements TickEventListener, OrderEventListener {
    
    private Ticker ticker;
    
    @Inject
    protected TapeReader(Ticker ticker) {
        this.ticker = ticker;
    }
    
    public void readTheTape() throws Exception {
        ticker.start();
    }

    @Override
    public void onTick(Tick tick) {
        System.out.println(tick);
    }

    @Override
    public void onOrder(Order order) {
        // TODO Auto-generated method stub
        
    }
    
}