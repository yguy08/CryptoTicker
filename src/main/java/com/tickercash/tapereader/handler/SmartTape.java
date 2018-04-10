package com.tickercash.tapereader.handler;

import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;
import com.google.inject.Inject;
import com.tickercash.tapereader.framework.EventHandler;
import com.tickercash.tapereader.framework.TipStatement;
import com.tickercash.tapereader.listener.TipEventListener;
import com.tickercash.tapereader.model.Tick;
import com.tickercash.tapereader.model.Tip;

public class SmartTape implements EventHandler, UpdateListener {
    
    private EPServiceProvider engine;
    
    private EPStatement statement;

    private TipEventListener listener;
    
    @Inject
    public SmartTape(TipStatement tipStmt, TipEventListener listener) {
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
        listener.onTip(new Tip());
    }

}
