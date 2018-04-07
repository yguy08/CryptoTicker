package com.tickercash.tapereader.ticker.fake;

import com.google.inject.Inject;
import com.tickercash.tapereader.marketdata.Tick;
import com.tickercash.tapereader.tape.Tape;
import com.tickercash.tapereader.ticker.DefaultTicker;

public class FakeTicker extends DefaultTicker {
    
    @Inject
    protected FakeTicker(Tape tape) {
        super(tape);
    }

    @Override
    public void writeToTape() throws Exception {
        running.set(true);
        while(running.get()) {
            tape.write(new Tick());
            Thread.sleep(1000);
        }
    }

}
