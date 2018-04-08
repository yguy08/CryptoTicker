package com.tickercash.tapereader.tip.listener;

import com.espertech.esper.client.EventBean;

public interface TipListener {
    void onTip(EventBean[] newTick, EventBean[] oldTick);
}
