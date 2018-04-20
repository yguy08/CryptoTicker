package com.tapereader.ticker;

import java.util.concurrent.atomic.AtomicBoolean;

import com.google.inject.Inject;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.tapereader.framework.DisruptorClerk;
import com.tapereader.framework.Event;
import com.tapereader.framework.MarketEvent;
import com.tapereader.framework.Ticker;
import com.tapereader.framework.Transmitter;
import com.tapereader.handler.CounterHandler;
import com.tapereader.util.UniqueCurrentTimeMS;

import net.openhft.chronicle.bytes.Bytes;
import net.openhft.chronicle.wire.TextWire;
import net.openhft.chronicle.wire.Wire;

public class FakeTickerWireImpl implements Ticker, EventHandler<Event> {
    
    protected final AtomicBoolean running = new AtomicBoolean(false);
    
    private final Transmitter transmitter;
    
    private final Disruptor<Event> disruptor;
    
    private final RingBuffer<Event> ringBuffer;
    
    @Inject
    protected FakeTickerWireImpl(Transmitter transmitter) {
        this.transmitter = transmitter;
        disruptor = DisruptorClerk.newMarketEventDisruptor();
        ringBuffer = disruptor.getRingBuffer();
        disruptor.handleEventsWith(this, new CounterHandler());
        disruptor.start();
    }

    @Override
    public void start() throws Exception {
        running.set(true);
        Wire wire = new TextWire(Bytes.elasticByteBuffer());
        while (running.get()) {
            new MarketEvent("BTC/USD","FAKE",UniqueCurrentTimeMS.uniqueCurrentTimeMS()).writeMarshallable(wire);
            ringBuffer.publishEvent(this::translateTo, wire);
            Thread.sleep(1000);
        }
    }
    
    private final void translateTo(Event event, long sequence, Wire wire){
        event.readMarshallable(wire);
    }

    @Override
    public void onEvent(Event event, long sequence, boolean endOfBatch) throws Exception {
        transmitter.transmit(event);
    }
}