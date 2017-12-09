package com.tickercash.tapereader.core;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.poloniex.dto.marketdata.PoloniexChartData;
import org.knowm.xchange.poloniex.service.PoloniexChartDataPeriodType;
import org.knowm.xchange.poloniex.service.PoloniexMarketDataServiceRaw;

import com.tickercash.tapereader.event.Tick;

public class HistoricalService {
	
	public static Exchange EXCHANGE = null;

	private static String file; 
	
	private static BufferedWriter buffWriter;
	
	private static final Logger LOGGER = LogManager.getLogger("Save2File");
	
	public static void main(String[] args) {
        file = "ticks-"+UniqueCurrentTimeMS.uniqueCurrentTimeMS()+".csv";
		
		try {
			buffWriter = new BufferedWriter(new FileWriter(file));
		} catch (IOException e) {
			LOGGER.error(e);
		};
		
		Instant now = Instant.now();
		Instant start = now.minusSeconds(86400 * 55);
		
		EXCHANGE = TickerFunction.XCHANGEFUNC.apply("poloniex");
		
		savePoloniexTicks(start.getEpochSecond(),now.getEpochSecond());
		
	}
	
	private static void savePoloniexTicks(long start, long end){
		List<CurrencyPair> pairList = EXCHANGE.getExchangeSymbols();
		List<PoloniexChartData> poloniexChartData = null;
		for(CurrencyPair pair : pairList){
			LOGGER.info("Saving... {} ", pair);
			try {
				poloniexChartData = (Arrays.asList(((PoloniexMarketDataServiceRaw) EXCHANGE.getMarketDataService())
						.getPoloniexChartData(pair, start,
						end, PoloniexChartDataPeriodType.PERIOD_7200)));
			} catch (Exception e) {
				LOGGER.error(e);
				poloniexChartData = null;
			}			

			if(poloniexChartData != null) {
				for(PoloniexChartData pcd : poloniexChartData) {
					try {
						save(new Tick(pair.toString(),pcd.getDate().getTime(),pcd.getClose().doubleValue()));
					} catch(Exception e) {
						LOGGER.error(e);
					}
				}
			}
			
			LOGGER.info("Saved! {} ", pair);
		}
	}
	
	private static void save(Tick tick) throws IOException {
		buffWriter.append('\n');
		buffWriter.append(tick.toString());		
		buffWriter.flush();
	}
	
	

}
