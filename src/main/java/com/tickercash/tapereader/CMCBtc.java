package com.tickercash.tapereader;

import java.time.LocalDate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.tickercash.marketdata.Tick;
import com.tickercash.quoteboy.cmc.CMCQuoteBoy;
import com.tickercash.util.CsvWriter;

@SuppressWarnings("unchecked")
public class CMCBtc extends TapeReader {
	
	private static final Logger LOGGER = LogManager.getLogger("CMCReadAll");
	
	private final CsvWriter writer;
	
	public CMCBtc() {
		setQuoteBoy(new CMCQuoteBoy(60*24*1000));
		writer = new CsvWriter("ticks/cmc/btc/CMCBTCPairs-"+LocalDate.now()+".csv");		
	}
	
	@Override
    public void onTick(Tick tick, long sequence, boolean endOfBatch) {
		LOGGER.info("New Tick: {}", tick);
		writer.write(tick.toString());
    }    
	
	@Override
    public void readTheTape() {
    	getQuoteBoy().addHandler(this::onTick);
    	getQuoteBoy().start();
    }

}
