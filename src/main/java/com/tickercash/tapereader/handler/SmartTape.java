package com.tickercash.tapereader.handler;

import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;
import com.google.inject.Inject;
import com.tickercash.tapereader.framework.Handler;
import com.tickercash.tapereader.framework.Listener;
import com.tickercash.tapereader.framework.TipStatement;
import com.tickercash.tapereader.model.Tick;

public class SmartTape implements Handler, UpdateListener {
    
    private EPServiceProvider engine;
    
    private EPStatement statement;

    private Listener listener;
    
    @Inject
    public SmartTape(TipStatement tipStmt, Listener listener) {
        engine = EPServiceProviderManager.getDefaultProvider();
        engine.getEPAdministrator().getConfiguration().addEventType(Tick.class);
        statement = engine.getEPAdministrator().createEPL(tipStmt.toString());
        statement.addListener(this);
        this.listener = listener;
    }

    @Override
    public void onEvent(Tick event, long sequence, boolean endOfBatch) throws Exception {
        engine.getEPRuntime().sendEvent(event);
    }

    @Override
    public void update(EventBean[] newEvents, EventBean[] oldEvents) {
        String symbol = (String) newEvents[0].get("symbol");
        String feed = (String) newEvents[0].get("feed");
        long timestamp = (Long) newEvents[0].get("timestamp");
        double last = (Double) newEvents[0].get("last");
        listener.onEvent(new Tick(symbol,feed,timestamp,last));
    }

}
