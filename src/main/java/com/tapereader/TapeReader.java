package com.tapereader;

import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import com.tapereader.framework.TapeGateway;
import com.tapereader.framework.Ticker;
import com.tapereader.listener.OrderEventListener;
import com.tapereader.listener.TickEventListener;
import com.tapereader.model.Order;
import com.tapereader.model.Tick;

public class TapeReader implements TickEventListener, OrderEventListener {
    
    private Ticker ticker;
    
    private TapeGateway tape;
    
    @Inject
    protected TapeReader(Ticker ticker, TapeGateway tape) {
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
    public void update(Tick tick) {
        System.out.println(tick);
    }

    @Override
    public void onOrder(Order order) {
        
    }
    
    @Subscribe
    public void update(String event){
    	System.out.println("Tape Reader:"+event);
    }
    
}