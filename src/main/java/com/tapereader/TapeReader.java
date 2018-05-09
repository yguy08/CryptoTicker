package com.tapereader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.inject.Inject;
import com.tapereader.framework.Engine;
import com.tapereader.framework.OrderEventListener;
import com.tapereader.framework.Receiver;
import com.tapereader.framework.TickEventListener;
import com.tapereader.framework.Ticker;
import com.tapereader.model.Order;
import com.tapereader.model.Tick;

public class TapeReader {
    
	protected Ticker ticker;
    
    protected Receiver receiver;
    
    protected Engine engine;
    
    protected static final Logger LOGGER = LogManager.getLogger("TapeReader");
    
    @Inject
    protected TapeReader(Ticker ticker, Receiver receiver, Engine engine) {
        this.ticker = ticker;
        this.receiver = receiver;
        this.engine = engine;
    }
    
    public Engine getEngine(){
    	return engine;
    }
    
    public Ticker getTicker() {
        return ticker;
    }

    public void setTicker(Ticker ticker) {
        this.ticker = ticker;
    }

    public Receiver getReceiver() {
        return receiver;
    }

    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
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
        receiver.initialize(engine);
        receiver.read();
    }

    public void onTick(Tick tick) {
        System.out.println(tick);
    }

    public void onOrder(Order order) {
        
    }
    
}