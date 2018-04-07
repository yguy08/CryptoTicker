package com.tickercash.tapereader.ticker;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;

import com.google.inject.Inject;
import com.tickercash.tapereader.tape.Tape;

public abstract class DefaultTicker implements Ticker {
    
    protected final Tape tape;
    
    protected final AtomicBoolean running = new AtomicBoolean(false);
    
    protected final ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
    
    @Inject
    protected DefaultTicker(Tape tape) {
        this.tape = tape;
    }

    public Tape getTape() {
        return tape;
    }
    
}
