package com.tickercash.tapereader.tip;

import com.lmax.disruptor.EventHandler;
import com.tickercash.tapereader.marketdata.Tick;

public interface TipListener extends EventHandler<Tick> {

}
