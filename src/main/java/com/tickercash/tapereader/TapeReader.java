package com.tickercash.tapereader;

import java.time.LocalDateTime;
import java.util.List;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;
import com.lmax.disruptor.EventHandler;
import com.tickercash.tapereader.clerk.HistoricalDataClerk;
import com.tickercash.tapereader.clerk.QuoteBoy;
import com.tickercash.tapereader.config.Config;
import com.tickercash.tapereader.marketdata.Tick;
import com.tickercash.tapereader.tip.TipEngine;
import com.tickercash.tapereader.tip.TipEngineImpl;
import com.tickercash.tapereader.wire.Receiver;

public class TapeReader {
    
    private Config config;
	
	private QuoteBoy quoteBoy;
	
	private TipEngine tipEngine;
	
	private Receiver receiver;
		
	public void setConfig(Config config) {
	    this.config = config;
	}
	
	public Config getConfig() {
	    return config;
	}
	
	public void setQuoteBoy(QuoteBoy quoteBoy) {
		this.quoteBoy = quoteBoy;
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
	
	protected void onEvent(Tick event, long sequence, boolean endOfBatch) throws Exception {
		tipEngine.sendNewTick(event);
	}
	
	protected void update(EventBean[] newTick, EventBean[] oldTick){
        String symbol = (String) newTick[0].get("symbol");
        String feed = (String) newTick[0].get("feed");
        long timestamp = (Long) newTick[0].get("timestamp");
        double last = (Double) newTick[0].get("last");
        int volume = (Integer) newTick[0].get("volume");
        onTick(new Tick(symbol, feed, timestamp, last));
	}
	
	public void init() throws Exception {
		setQuoteBoy(QuoteBoy.createQuoteBoy(config.getQuoteBoy()));
		setTipEngine(new TipEngineImpl());
		setReceiver(new Receiver(quoteBoy.getTopicName()));
		addEventHandler(this::onEvent);
	}
	
	public void readTheTape() throws Exception {
		String tip = "select symbol, feed, timestamp, last from Tick";
        tipEngine.addStatement(tip);
		addTipListener(this::update);
		
		if(getConfig().getPreFeed()) {
		    HistoricalDataClerk hClerk = HistoricalDataClerk.createHistoricalDataClerk(config.getQuoteBoy());
		    List<Tick> hTicks = hClerk.getHistoricalTicks("bitcoin", LocalDateTime.of(2017, 12, 01, 01, 00), LocalDateTime.of(2019, 12, 01, 01, 00));
		    for(Tick tick : hTicks) {
		        getTipEngine().sendNewTick(tick);
		    }
		}
		
		receiver.startReceiving();
		
	}

}
