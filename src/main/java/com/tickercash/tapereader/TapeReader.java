package com.tickercash.tapereader;

import java.io.File;
import java.time.Instant;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;
import com.espertech.esper.client.deploy.DeploymentOptions;
import com.espertech.esper.client.deploy.EPDeploymentAdmin;
import com.espertech.esper.client.deploy.Module;
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
        double last = (Double) newTick[0].get("last");
        long ts = (Long) newTick[0].get("timestamp");
        double max = (Double) newTick[0].get("maxClose");
        System.out.println(Instant.ofEpochSecond(ts).toString()+ " Last "+ last + " MAX: " + max);
    }
    
    public void init() throws Exception {
        setQuoteBoy(QuoteBoy.createQuoteBoy(config.getQuoteBoyType()));
        if(config.getQuoteBoyType()==QuoteBoyType.CSV){
            ((CsvQuoteBoy) getQuoteBoy()).init(config.getReadCsvPath());
        }
        setTipEngine(new TipEngineImpl());
        setReceiver(new Receiver(quoteBoy.getTopicName()));
        addEventHandler(this::onEvent);
        EPDeploymentAdmin deployAdmin = getTipEngine().getEngine().getEPAdministrator().getDeploymentAdmin();
        Module module = deployAdmin.read(new File("src/main/resources/DoubleYou.epl"));
        deployAdmin.deploy(module, new DeploymentOptions());
        getTipEngine().getEngine().getEPAdministrator().getStatement(config.getTip()).addListener(this::update);
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