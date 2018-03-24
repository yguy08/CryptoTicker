package com.tickercash.tapereader.tip;

import com.espertech.esper.client.UpdateListener;
import com.tickercash.tapereader.event.Tick;

public interface TipEngine {
    
    public void addListener(UpdateListener listener);
    
    void sendNewTick(Tick tick);
}
