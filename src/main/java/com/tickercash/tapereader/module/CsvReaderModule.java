package com.tickercash.tapereader.module;

import com.google.inject.AbstractModule;
import com.tickercash.tapereader.TapeReader;
import com.tickercash.tapereader.framework.EventHandler;
import com.tickercash.tapereader.framework.Ticker;
import com.tickercash.tapereader.framework.TipStatement;
import com.tickercash.tapereader.handler.SmartTape;
import com.tickercash.tapereader.listener.OrderEventListener;
import com.tickercash.tapereader.listener.TickEventListener;
import com.tickercash.tapereader.ticker.CsvTicker;
import com.tickercash.tapereader.tip.trend.BuyHighSellLow;

public class CsvReaderModule extends AbstractModule {
    @Override
    protected void configure() {
        install(new PropertiesModule());
        bind(Ticker.class).to(CsvTicker.class);
        bind(EventHandler.class).to(SmartTape.class);
        bind(TipStatement.class).to(BuyHighSellLow.class);
        bind(TickEventListener.class).to(TapeReader.class);
        bind(OrderEventListener.class).to(TapeReader.class);
    }
}
