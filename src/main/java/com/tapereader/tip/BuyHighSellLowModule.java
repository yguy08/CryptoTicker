package com.tapereader.tip;

import com.google.inject.AbstractModule;
import com.tickercash.tapereader.config.EsperConfig;
import com.tickercash.tapereader.config.EsperModule;
import com.tickercash.tapereader.framework.Tape;
import com.tickercash.tapereader.framework.Ticker;
import com.tickercash.tapereader.listener.OrderEventListener;
import com.tickercash.tapereader.listener.TickEventListener;
import com.tickercash.tapereader.tape.SmartTape;
import com.tickercash.tapereader.ticker.FakeTicker;

public class BuyHighSellLowModule extends AbstractModule {
    
    String config = "src/main/resources/BuyHighSellLow.esper.cfg.xml";
    String module = "src/main/resources/BuyHighSellLow.epl";

    @Override
    protected void configure() {
        bind(Ticker.class).to(FakeTicker.class);
        bind(Tape.class).to(SmartTape.class);
        bindConstant().annotatedWith(EsperConfig.class).to(config);
        bindConstant().annotatedWith(EsperModule.class).to(module);
        bind(TickEventListener.class).to(BuyHighSellLow.class);
        bind(OrderEventListener.class).to(BuyHighSellLow.class);
    }

}
