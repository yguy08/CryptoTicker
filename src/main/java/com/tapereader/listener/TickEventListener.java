package com.tapereader.listener;

import com.tapereader.model.Tick;

public interface TickEventListener {
    void onTick(Tick tick);
}
