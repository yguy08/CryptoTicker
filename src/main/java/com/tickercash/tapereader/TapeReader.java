package com.tickercash.tapereader;

import java.time.Instant;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.LifecycleAware;
import com.tickercash.tapereader.clerk.CsvQuoteBoy;
import com.tickercash.tapereader.clerk.QuoteBoy;
import com.tickercash.tapereader.clerk.QuoteBoyType;
import com.tickercash.tapereader.config.Config;
import com.tickercash.tapereader.marketdata.Tick;
import com.tickercash.tapereader.tip.TipEngine;
import com.tickercash.tapereader.tip.TipEngineImpl;
import com.tickercash.tapereader.wire.Receiver;
import com.tickercash.tapereader.wire.Transmitter;

public class TapeReader implements EventHandler<Tick>, LifecycleAware, UpdateListener {
    
    private Config config;
    
    private TipEngine tipEngine;
    
    private QuoteBoy quoteBoy;
    
    private Receiver receiver;
    
    private Transmitter transmitter;
    
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
    
    public void setReceiver(Receiver receiver) {
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
    	String symbol = (String) newTick[0].get("symbol");
        //long ts = (Long) newTick[0].get("timestamp");
        //ts/=1000; // nanos
        //double last = (Double) newTick[0].get("last");
        //double max = (Double) newTick[0].get("maxLast");
        //System.out.println(symbol+" "+Instant.ofEpochSecond(ts).toString()+" Last: "+ last 
        	//	+ " MAX: " + max);
        System.out.println(newTick);
    }
    
    public void init() throws Exception {
        setQuoteBoy(QuoteBoy.createQuoteBoy(config.getQuoteBoyType()));
        if(config.getQuoteBoyType()==QuoteBoyType.CSV){
            ((CsvQuoteBoy) getQuoteBoy()).init(config.getReadCsvPath());
        }
        setTipEngine(new TipEngineImpl());
        setReceiver(new Receiver(quoteBoy.getTopicName()));
        addEventHandler(this::onEvent);
        getTipEngine().deployModule(config.getTip());
        getTipEngine().addListener(config.getTip(),this::update);
    }
    
    public void readTheTape() throws Exception {
        String tip = "select symbol, feed, timestamp, last from Tick";
        tipEngine.addStatement(tip);
        addTipListener(this::update);
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

    public Transmitter getTransmitter() {
        return transmitter;
    }

    public void setTransmitter(Transmitter transmitter) {
        this.transmitter = transmitter;
    }
}