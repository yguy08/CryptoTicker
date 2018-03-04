package com.tickercash.clerk.cmc;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;

import com.tickercash.clerk.HistoricalDataClerk;
import com.tickercash.marketdata.Tick;

public class CMCHistoricalDataClerk implements HistoricalDataClerk {
	
	private static final DateTimeFormatter cmcDateFormat = DateTimeFormatter.ofPattern("MMM dd, yyyy");
	private static final String cmcHistoricalDateElementId = "historical-data";
	
	public CMCHistoricalDataClerk() {}

	@Override
	public List<Tick> getHistoricalTicks(String symbol, long start, long end) {
		return null;
	}
	
	@Override
	public List<Tick> getHistoricalTicks(String symbol, String start, String end) throws Exception {
		String url = "https://coinmarketcap.com/currencies/"+symbol+"/historical-data/?start="+start+"&end="+end;
		return Jsoup.connect(url).get()
		        .getElementById(cmcHistoricalDateElementId)
		        .getElementsByTag("tr")
		        .stream()
		        .skip(1)
		        .map(td -> td.getElementsByTag("td"))
		        .map(t -> new Tick(
		        		symbol, 
		        		cmcDateToEpochSeconds(t.get(0).text()), 
		        		Double.parseDouble(t.get(4).text())))
		        .collect(Collectors.toList());
	}
	
	/**
	 * Returns epoch seconds from CMC historical format date (MMM dd, yyyy) 
	 * @param date in MMM dd, yyyy format
	 * @return epoch seconds
	 */
	private static long cmcDateToEpochSeconds(String dateStr){
		LocalDate localDate = LocalDate.parse(dateStr, cmcDateFormat);
		LocalDateTime localDateTime = LocalDateTime.of(localDate, LocalTime.MIDNIGHT);
		return localDateTime.toEpochSecond(ZoneOffset.UTC);
	}

}
