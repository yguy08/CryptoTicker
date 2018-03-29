package com.tickercash.tapereader.clerk;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;
import com.lmax.disruptor.EventHandler;
import com.tickercash.tapereader.marketdata.Tick;
import com.tickercash.tapereader.tip.TipEngine;
import com.tickercash.tapereader.tip.TipEngineImpl;
import com.tickercash.tapereader.wire.Receiver;

public class TapeReader {
	
	private QuoteBoy quoteBoy;
	
	private TipEngine tipEngine;
	
	private Receiver receiver;
	
	private EventHandler<Tick> tickEventHandler;
	
	private UpdateListener tipListener;
	
	private String tip = "select symbol, feed, timestamp, last from Tick";
	
	public void setQuoteBoy(QuoteBoy quoteBoy) {
		this.quoteBoy = quoteBoy;
	}
	
    public void setTipEngine(TipEngine tipEngine) {
        this.tipEngine = tipEngine;
    }
	
	public void setReceiver(Receiver receiver) {
		this.receiver = receiver;
	}
	
	public void setEventHandler(EventHandler<Tick> handler) {
		receiver.setEventHandler(handler);
	}
	
	public void setTipListener(UpdateListener listener) {
	    tipEngine.addListener(listener);
	}
	
	public void onTick(Tick tick) {
        System.out.println(String.format("BUY: Symbol: %s, Feed: %s, Timestamp: %d, Last: %f", 
                tick.getSymbol(), tick.getFeed(), tick.getTimestamp(), tick.getLast()));
	}
	
	public void readTheTape() throws Exception {
		setQuoteBoy(new FakeQuoteBoy());
		setTipEngine(new TipEngineImpl());
		setReceiver(new Receiver(quoteBoy.getTopicName()));
		
		tickEventHandler = new EventHandler<Tick>() {
		    @Override
            public void onEvent(Tick event, long sequence, boolean endOfBatch) throws Exception {
                tipEngine.sendNewTick(event);
            }
		};
		
		setEventHandler(tickEventHandler);
		
        tipEngine.addStatement(tip);
		tipListener = new UpdateListener() {
            @Override
            public void update(EventBean[] newTick, EventBean[] oldTick) {
                String symbol = (String) newTick[0].get("symbol");
                String feed = (String) newTick[0].get("feed");
                long timestamp = (Long) newTick[0].get("timestamp");
                double last = (Double) newTick[0].get("last");
                onTick(new Tick(symbol, feed, timestamp, last));
            }		    
		};
		
		setTipListener(tipListener);
		
		receiver.startReceiving();
		
	}

}
