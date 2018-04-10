package com.tickercash.tapereader.module;

import com.google.inject.AbstractModule;
import com.tickercash.tapereader.TapeReader;
import com.tickercash.tapereader.framework.Tape;
import com.tickercash.tapereader.framework.Ticker;
import com.tickercash.tapereader.framework.TipStatement;
import com.tickercash.tapereader.handler.SmartTape;
import com.tickercash.tapereader.listener.OrderEventListener;
import com.tickercash.tapereader.listener.TickEventListener;
import com.tickercash.tapereader.ticker.FakeTicker;
import com.tickercash.tapereader.tip.trend.DefaultTipStatement;

public class TapeReaderModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(Ticker.class).to(FakeTicker.class);
        bind(Tape.class).to(SmartTape.class);
        bind(TipStatement.class).to(DefaultTipStatement.class);
        bind(TickEventListener.class).to(TapeReader.class);
        bind(OrderEventListener.class).to(TapeReader.class);
    }

}
