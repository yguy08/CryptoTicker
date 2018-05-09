package com.tapereader.framework;

import com.tapereader.model.Tick;

public interface TickEventListener {
    void onTick(Tick tick);
}
