package com.tickercash.tapereader.tape;

import com.google.inject.Inject;
import com.tickercash.tapereader.clerk.QuoteBoyType;
import com.tickercash.tapereader.marketdata.Tick;
import com.tickercash.tapereader.tip.TipListener;
import com.tickercash.tapereader.util.UniqueCurrentTimeMS;

public class EmbeddedTape extends DefaultTape {
    
    @Inject
    protected EmbeddedTape(TipListener tip) {
        super(tip);
    }

    @Override
    public void write(Tick tick) throws Exception {
        ringBuffer.publishEvent(this::translateTo);
    }
    
    protected final void translateTo(Tick event, long sequence){
        event.set("BTC/USD", QuoteBoyType.FAKE.name(), UniqueCurrentTimeMS.uniqueCurrentTimeMS(), 1000.00);
    }

    @Override
    public void read(TipListener tip) throws Exception {
        
    }

}
