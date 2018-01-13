package com.tickercash.tapereader;

import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.UpdateListener;
import com.tickercash.model.Tick;

public class TipEngine {
    
    private EPServiceProvider engine;
    private EPStatement statement;

    public TipEngine(String tip) {
        engine = EPServiceProviderManager.getDefaultProvider();
        engine.getEPAdministrator().getConfiguration().addEventType(Tick.class);
        statement = engine.getEPAdministrator().createEPL(tip);
    }
    
    public void sendEvent(Tick tick){
        engine.getEPRuntime().sendEvent(tick);
    }
    
    public void addListener(UpdateListener listener){
        statement.addListener(listener);
    }

}
