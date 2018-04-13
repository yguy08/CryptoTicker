package com.tapereader.listener;

import com.tapereader.model.Tick;

public interface TickEventListener {
    void update(Tick tick);
}
