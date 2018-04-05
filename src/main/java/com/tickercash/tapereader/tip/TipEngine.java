package com.tickercash.tapereader.tip;

import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.UpdateListener;
import com.tickercash.tapereader.marketdata.Tick;

public interface TipEngine {
    
    public void addListener(UpdateListener listener);
    
    void addStatement(String stmt);
    
    EPServiceProvider getEngine();
    
    void deployModule(String eplFile) throws Exception;
    
    void addListener(String stmtName, UpdateListener listener);
    
    void sendNewTick(Tick tick);
}
