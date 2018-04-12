package com.tapereader.handler;

import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;
import com.google.inject.Inject;
import com.tapereader.config.EsperStatement;
import com.tapereader.framework.Tape;
import com.tapereader.listener.TickEventListener;
import com.tapereader.model.Tick;

public class SmartTape implements Tape, UpdateListener {
    
    private EPServiceProvider engine;
    
    private EPStatement statement;

    private TickEventListener listener;
    
    @Inject
    public SmartTape(@EsperStatement String esperPath, TickEventListener listener) {
        engine = EPServiceProviderManager.getDefaultProvider();
        engine.getEPAdministrator().getConfiguration().addEventType(Tick.class);
        statement = engine.getEPAdministrator().createEPL(esperPath);
        statement.addListener(this);
        this.listener = listener;
    }

    @Override
    public void update(EventBean[] newEvents, EventBean[] oldEvents) {
        String symbol = (String) newEvents[0].get("symbol");
        String feed = (String) newEvents[0].get("feed");
        long timestamp = (Long) newEvents[0].get("timestamp");
        double last = (Double) newEvents[0].get("last");
        listener.onTick(new Tick(symbol, feed, timestamp, last));
    }

    @Override
    public void onEvent(Tick event, long sequence, boolean endOfBatch) throws Exception {
        engine.getEPRuntime().sendEvent(event);
    }

}
