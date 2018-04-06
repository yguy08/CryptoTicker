package com.tickercash.tapereader;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;
import com.google.inject.Inject;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.LifecycleAware;
import com.tickercash.tapereader.clerk.QuoteBoy;
import com.tickercash.tapereader.config.Config;
import com.tickercash.tapereader.marketdata.Tick;
import com.tickercash.tapereader.tip.TipEngine;
import com.tickercash.tapereader.wire.AbstractReceiver;
import com.tickercash.tapereader.wire.AirTransmitter;
import com.tickercash.tapereader.wire.Receiver;

public class TapeReader implements EventHandler<Tick>, LifecycleAware, UpdateListener {
    
    private Config config;
    
    private QuoteBoy quoteBoy;
    
    private TipEngine tipEngine;
    
    private AirTransmitter transmitter;
    
    private Receiver receiver;
    
    @Inject
    protected TapeReader(QuoteBoy quoteBoy, TipEngine engine, Receiver receiver){
        this.quoteBoy = quoteBoy;
        this.tipEngine = engine;
        this.receiver = receiver;
    }
    
    public void setConfig(Config config) {
        this.config = config;
    }
    
    public Config getConfig() {
        return config;
    }
    
    public void setQuoteBoy(QuoteBoy quoteBoy) {
        this.quoteBoy = quoteBoy;
    }
    
    public QuoteBoy getQuoteBoy(){
        return quoteBoy;
    }
    
    public void setTipEngine(TipEngine tipEngine) {
        this.tipEngine = tipEngine;
    }
    
    public TipEngine getTipEngine() {
        return tipEngine;
    }
    
    public void setReceiver(AbstractReceiver receiver) {
        this.receiver = receiver;
    }
    
    public Receiver getReceiver(){
        return receiver;
    }
    
    public void addEventHandler(EventHandler<Tick> handler) {
        receiver.setEventHandler(handler);
    }
    
    public void addTipListener(UpdateListener listener) {
        tipEngine.addListener(listener);
    }
    
    public void onTick(Tick tick) {
        System.out.println(String.format("BUY: Symbol: %s, Feed: %s, Timestamp: %d, Last: %f", 
                tick.getSymbol(), tick.getFeed(), tick.getTimestamp(), tick.getLast()));
    }
    
    public void onEvent(Tick event, long sequence, boolean endOfBatch) throws Exception {
        tipEngine.sendNewTick(event);
    }
    
    public void update(EventBean[] newTick, EventBean[] oldTick) {
    	System.out.println(newTick);
    }
    
    public void init() throws Exception {
        
    }
    
    public void readTheTape() throws Exception {
        Thread t = new Thread(() -> {
            try {
                getQuoteBoy().getQuotes();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });
        
        t.setDaemon(true);
        t.start();
        
        String tip = "select symbol, feed, timestamp, last from Tick";
        tipEngine.addStatement(tip);
        addTipListener(this::update);
        addEventHandler(this::onEvent);
        receiver.startReceiving();
    }

    @Override
    public void onStart() {
        if(config.getPreFeed()){
            System.out.println("PreFeed...");
        }
    }

    @Override
    public void onShutdown() {
        
    }

    public AirTransmitter getTransmitter() {
        return transmitter;
    }

    public void setTransmitter(AirTransmitter transmitter) {
        this.transmitter = transmitter;
    }
}