package com.tickercash.tapereader.module;

import com.google.inject.AbstractModule;
import com.tickercash.tapereader.TapeReader;
import com.tickercash.tapereader.framework.Handler;
import com.tickercash.tapereader.framework.Listener;
import com.tickercash.tapereader.framework.TipStatement;
import com.tickercash.tapereader.handler.SmartTape;
import com.tickercash.tapereader.ticker.PoloTicker;
import com.tickercash.tapereader.ticker.Ticker;
import com.tickercash.tapereader.tip.trend.DefaultTipStatement;

public class TapeReaderModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(Ticker.class).to(PoloTicker.class);
        bind(Handler.class).to(SmartTape.class);
        bind(TipStatement.class).to(DefaultTipStatement.class);
        bind(Listener.class).to(TapeReader.class);
    }

}
