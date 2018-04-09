package com.tickercash.tapereader.framework;

import com.tickercash.tapereader.model.Tick;

public interface Handler {
    public void onEvent(Tick event, long sequence, boolean endOfBatch) throws Exception;
}
