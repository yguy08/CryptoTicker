package com.tapereader.ticker;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.concurrent.atomic.AtomicBoolean;

import org.h2.tools.Csv;

import com.google.inject.Inject;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.tapereader.config.CsvFile;
import com.tapereader.framework.DisruptorClerk;
import com.tapereader.framework.Ticker;
import com.tapereader.framework.Transmitter;
import com.tapereader.handler.CounterHandler;
import com.tapereader.model.Tick;

public class CsvTicker implements Ticker {
    
    protected final AtomicBoolean running = new AtomicBoolean(false);
    
    private final Disruptor<Tick> disruptor;
    
    private final RingBuffer<Tick> ringBuffer;
    
    private final String path;
    
    @SuppressWarnings("unchecked")
    @Inject
    protected CsvTicker(@CsvFile String path, Transmitter transmitter) {
        this.path = path;
        disruptor = DisruptorClerk.newTickDisruptor();
        ringBuffer = disruptor.getRingBuffer();
        disruptor.handleEventsWith(transmitter::transmit, new CounterHandler());
        disruptor.start();
    }

    @Override
    public void start() throws Exception {
        ResultSet rs = new Csv().read(path, null, null);
        while(rs.next()){
            String date = rs.getString(1);
            double last = rs.getDouble(2);
            ringBuffer.publishEvent(this::translateTo, date, last);
        }
        disruptor.shutdown();
    }
    
    private final void translateTo(Tick event, long sequence, String date, double last){
        long timestamp = LocalDateTime.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).toEpochSecond(ZoneOffset.UTC);
        event.set("BTC/USD", "CSV", timestamp, last);
    }

}
