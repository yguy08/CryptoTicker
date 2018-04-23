package com.tapereader.module;

import com.google.inject.AbstractModule;
import com.tapereader.TapeReader;
import com.tapereader.engine.DefaultEngine;
import com.tapereader.framework.Engine;
import com.tapereader.framework.Ticker;
import com.tapereader.listener.OrderEventListener;
import com.tapereader.listener.TickEventListener;
import com.tapereader.ticker.polo.PoloTicker;

public class TapeReaderModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(Ticker.class).to(PoloTicker.class);
        bind(Engine.class).to(DefaultEngine.class);
        bind(TickEventListener.class).to(TapeReader.class);
        bind(OrderEventListener.class).to(TapeReader.class);
    }

}
