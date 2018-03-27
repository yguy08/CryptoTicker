package com.tickercash.tapereader.tip;

import com.espertech.esper.client.UpdateListener;
import com.tickercash.tapereader.model.Tick;

public interface TipEngine {
    
    public void addListener(UpdateListener listener);
    
    void addEventType(Class<?> eventType);
    
    void addStatement(String stmt);
    
    void sendNewTick(Tick tick);
}
