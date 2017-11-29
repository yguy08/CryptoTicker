package com.speculation1000.cryptoticker.model;

public interface TickEvent {
    void onTick(Tick tick,long sequence, boolean endOfBatch) throws Exception;
}
