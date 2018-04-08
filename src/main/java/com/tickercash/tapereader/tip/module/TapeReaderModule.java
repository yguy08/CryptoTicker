package com.tickercash.tapereader.tip.module;

import com.google.inject.AbstractModule;
import com.tickercash.tapereader.tick.handler.Engine;
import com.tickercash.tapereader.tick.handler.TickHandler;
import com.tickercash.tapereader.ticker.PoloTicker;
import com.tickercash.tapereader.ticker.Ticker;
import com.tickercash.tapereader.tip.DefaultTip;
import com.tickercash.tapereader.tip.Tip;
import com.tickercash.tapereader.tip.listener.LogTipListener;
import com.tickercash.tapereader.tip.listener.TipListener;
import com.tickercash.tapereader.tip.statement.DefaultTipStatement;
import com.tickercash.tapereader.tip.statement.TipStatement;

public class TapeReaderModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(Tip.class).to(DefaultTip.class);
        bind(Ticker.class).to(PoloTicker.class);
        bind(TickHandler.class).to(Engine.class);
        bind(TipStatement.class).to(DefaultTipStatement.class);
        bind(TipListener.class).to(LogTipListener.class);
    }

}
