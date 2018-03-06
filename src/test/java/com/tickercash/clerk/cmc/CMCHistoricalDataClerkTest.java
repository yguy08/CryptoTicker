package com.tickercash.clerk.cmc;

import java.time.LocalDateTime;

import org.junit.Test;

import com.tickercash.clerk.HistoricalDataClerk;

public class CMCHistoricalDataClerkTest {
	
	private static LocalDateTime testDate = LocalDateTime.of(2018,3,5,8,55);

	@Test
	public void testBitcoinHistorical() throws Exception {
		HistoricalDataClerk hdc = new CMCHistoricalDataClerk();
		hdc.getHistoricalTicks("Bitcoin", testDate.minusYears(5), testDate)
		.stream().forEach(System.out::println);		
	}
	
}
