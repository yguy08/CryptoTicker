package com.tapereader;

import com.google.inject.Inject;
import com.tapereader.framework.Ticker;
import com.tapereader.listener.OrderEventListener;
import com.tapereader.listener.TickEventListener;
import com.tapereader.model.Order;
import com.tapereader.model.Tick;

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