package com.tickercash.tapereader.framework;

import com.tickercash.tapereader.model.Tick;

public interface Listener {
    void onEvent(Tick tick);
}
