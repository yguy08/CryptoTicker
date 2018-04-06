package com.tickercash.tapereader;

import com.google.inject.AbstractModule;
import com.tickercash.tapereader.clerk.QuoteBoy;
import com.tickercash.tapereader.clerk.fake.FakeQuoteBoy;
import com.tickercash.tapereader.tip.TipEngine;
import com.tickercash.tapereader.tip.TipEngineImpl;
import com.tickercash.tapereader.wire.AbstractReceiver;
import com.tickercash.tapereader.wire.AirTransmitter;
import com.tickercash.tapereader.wire.Receiver;
import com.tickercash.tapereader.wire.Transmitter;

public class TapeReaderModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(QuoteBoy.class).to(FakeQuoteBoy.class);
        bind(TipEngine.class).to(TipEngineImpl.class);
        bind(Transmitter.class).to(AirTransmitter.class);
        bind(Receiver.class).to(AbstractReceiver.class);
    }

}
