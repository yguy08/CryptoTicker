package com.tickercash.listener;

import com.espertech.esper.client.EventBean;

public interface TipListener {
    
    void onTick(EventBean[] arg0, EventBean[] arg1);

}
