package com.tickercash.tapereader.ticker;

import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

import org.h2.tools.Csv;

import com.tickercash.tapereader.framework.Transmitter;
import com.tickercash.tapereader.model.Tick;

public class CsvTicker extends AbstractTicker {
	
	private String path;
    
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    public CsvTicker(Transmitter transmitter) {
		super(transmitter);
	}

    @Override
    public void start() throws Exception {
        disruptor.start();
        ResultSet rs = new Csv().read(path, null, null);
        while(rs.next()){
            ringBuffer.publishEvent(this::translateTo, rs.getString(1), rs.getDouble(2));
        }
        disruptor.shutdown(10, TimeUnit.SECONDS);
    }
    
    private final void translateTo(Tick event, long sequence, String ts, double last){
        event.setSymbol("BTC/USD");
        event.setFeed(TickerType.CSV.toString());
        event.setTimestamp(LocalDateTime.parse(ts, formatter).toEpochSecond(ZoneOffset.UTC));
        event.setLast(last);
    }

}
