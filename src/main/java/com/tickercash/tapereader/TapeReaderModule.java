package com.tickercash.tapereader;

import com.google.inject.AbstractModule;
import com.tickercash.tapereader.tape.EmbeddedTape;
import com.tickercash.tapereader.tape.Tape;
import com.tickercash.tapereader.ticker.Ticker;
import com.tickercash.tapereader.ticker.fake.FakeTicker;
import com.tickercash.tapereader.tip.DefaultTip;
import com.tickercash.tapereader.tip.Tip;
import com.tickercash.tapereader.tip.TipConfirmedListener;
import com.tickercash.tapereader.tip.TipListener;

public class TapeReaderModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(TipListener.class).to(TipConfirmedListener.class);
        bind(Tip.class).to(DefaultTip.class);
        bind(Ticker.class).to(FakeTicker.class);
        bind(Tape.class).to(EmbeddedTape.class);
    }

}
