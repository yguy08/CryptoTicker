package com.tickercash.tip;

import com.espertech.esper.client.UpdateListener;
import com.tickercash.marketdata.Tick;

public interface TipEngine {
    
    public void addListener(UpdateListener listener);
    
    void sendNewTick(Tick tick);
}
