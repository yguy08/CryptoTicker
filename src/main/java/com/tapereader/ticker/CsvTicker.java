package com.tapereader.ticker;

import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import org.h2.tools.Csv;

import com.google.inject.Inject;
import com.lmax.disruptor.dsl.Disruptor;
import com.tapereader.annotation.CsvFile;
import com.tapereader.framework.Transmitter;
import com.tapereader.model.Tick;

public class CsvTicker extends AbstractTicker {
    
    private final String path;
    
    @Inject
    protected CsvTicker(@CsvFile String path, Transmitter transmitter) {
        super(transmitter);
        this.path = path;
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

    @Override
    public void handleEventsWith(Disruptor disruptor) {
        disruptor.handleEventsWith(transmitter::transmit);
    }

}
