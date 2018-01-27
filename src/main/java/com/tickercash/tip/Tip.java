package com.tickercash.tip;

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.UpdateListener;
import com.tickercash.marketdata.Tick;

public abstract class Tip {
    
    private EPServiceProvider engine;
    private EPStatement statement;
    private Configuration config;

    public Tip() {
    	config = new Configuration();
    }
    
    public void addEvent(String eventTypeName, String eventClass) {
    	config.addEventType(eventTypeName, eventClass);
    }
    
    public void initTipEngine() {
    	engine = EPServiceProviderManager.getDefaultProvider(config);
    }
    
    public void onTick(Tick tick, long sequence, boolean endOfBatch){
        engine.getEPRuntime().sendEvent(tick);
    }
    
    public void addListener(UpdateListener listener){
        statement.addListener(listener);
    }
    
    public void setStatement(String stmt) {
    	statement = engine.getEPAdministrator().createEPL(stmt);
    }

}
