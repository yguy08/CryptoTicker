package com.tickercash.tapereader.tick.handler;

import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.google.inject.Inject;
import com.tickercash.tapereader.tick.Tick;
import com.tickercash.tapereader.tip.listener.TipListener;
import com.tickercash.tapereader.tip.statement.TipStatement;

public class Engine implements TickHandler {
    
    private EPServiceProvider engine;
    
    private EPStatement statement;
    
    @Inject
    private Engine(TipStatement tipStmt, TipListener tipListener) {
        engine = EPServiceProviderManager.getDefaultProvider();
        engine.getEPAdministrator().getConfiguration().addEventType(Tick.class);
        statement = engine.getEPAdministrator().createEPL(tipStmt.toString());
        statement.addListener(tipListener::onTip);
    }

    @Override
    public void onTick(Tick event, long sequence, boolean endOfBatch) throws Exception {
        engine.getEPRuntime().sendEvent(event);
    }

}
