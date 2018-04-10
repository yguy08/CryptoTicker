package com.tickercash.tapereader.listener;

import com.tickercash.tapereader.model.Tick;

public interface TickEventListener {
    void onTick(Tick tick);
}
