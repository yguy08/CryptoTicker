package com.tapereader;

import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import com.tapereader.framework.MarketEvent;
import com.tapereader.framework.Receiver;
import com.tapereader.framework.Ticker;
import com.tapereader.listener.OrderEventListener;
import com.tapereader.listener.TickEventListener;
import com.tapereader.model.Order;
import com.tapereader.model.Tick;

public class TapeReader implements TickEventListener, OrderEventListener {
    
    private Ticker ticker;
    
    private Receiver tape;
    
    @Inject
    protected TapeReader(Ticker ticker, Receiver tape) {
        this.ticker = ticker;
        this.tape = tape;
    }
    
    public void readTheTape() throws Exception {
        Thread t = new Thread(() -> {
        	try{
        		ticker.start();
        	}catch(Exception e){

        	}
        });
        t.setDaemon(true);
        t.start();
        tape.initialize(this);
        tape.read();
    }

    @Override
    @Subscribe
    public void update(Tick tick) {
        System.out.println(tick);
    }

    @Override
    public void onOrder(Order order) {
        
    }
    
}