package com.tapereader.tip;

import com.google.inject.AbstractModule;
import com.tapereader.config.EsperConfig;
import com.tapereader.config.EsperModule;
import com.tapereader.framework.Tape;
import com.tapereader.framework.Ticker;
import com.tapereader.listener.OrderEventListener;
import com.tapereader.listener.TickEventListener;
import com.tapereader.ticker.FakeTicker;
import com.tickercash.tapereader.tape.SmartTape;

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
