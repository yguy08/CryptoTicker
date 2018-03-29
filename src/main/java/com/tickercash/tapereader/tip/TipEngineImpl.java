package com.tickercash.tapereader.tip;

import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.UpdateListener;
import com.tickercash.tapereader.marketdata.Tick;

public class TipEngineImpl implements TipEngine {
    
    private EPServiceProvider engine;
    
    private EPStatement statement;
    
    public TipEngineImpl() {
        engine = EPServiceProviderManager.getDefaultProvider();
        engine.getEPAdministrator().getConfiguration().addEventType(Tick.class);
    }

    public TipEngineImpl(Class<?> eventType, String stmt) {
        engine = EPServiceProviderManager.getDefaultProvider();
        engine.getEPAdministrator().getConfiguration().addEventType(eventType);
        statement = engine.getEPAdministrator().createEPL(stmt);
    }
    
    @Override
	public void addStatement(String stmt) {
		statement = engine.getEPAdministrator().createEPL(stmt);
	}
    
    public void addListener(UpdateListener listener){
        statement.addListener(listener);
    }

    @Override
    public void sendNewTick(Tick tick) {
        engine.getEPRuntime().sendEvent(tick);
    }

}
