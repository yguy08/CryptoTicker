package com.tapereader.tip;

import com.google.inject.AbstractModule;
import com.tickercash.tapereader.TapeReader;
import com.tickercash.tapereader.config.EsperStatement;
import com.tickercash.tapereader.framework.Tape;
import com.tickercash.tapereader.framework.Ticker;
import com.tickercash.tapereader.handler.SmartTape;
import com.tickercash.tapereader.listener.OrderEventListener;
import com.tickercash.tapereader.listener.TickEventListener;
import com.tickercash.tapereader.ticker.FakeTicker;

public class BuyHighSellLowModule extends AbstractModule {
    
    String select = "Select * from Tick";

    @Override
    protected void configure() {
        bind(Ticker.class).to(FakeTicker.class);
        bind(Tape.class).to(SmartTape.class);
        bindConstant().annotatedWith(EsperStatement.class).to(select);
        bind(TickEventListener.class).to(TapeReader.class);
        bind(OrderEventListener.class).to(TapeReader.class);
    }

}
