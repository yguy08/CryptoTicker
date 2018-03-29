package com.tickercash.tapereader.tip;

import com.espertech.esper.client.UpdateListener;
import com.tickercash.tapereader.marketdata.Tick;

public interface TipEngine {
    
    public void addListener(UpdateListener listener);
    
    void addStatement(String stmt);
    
    void sendNewTick(Tick tick);
}
