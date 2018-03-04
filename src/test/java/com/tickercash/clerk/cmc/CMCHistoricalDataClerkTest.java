package com.tickercash.clerk.cmc;

import com.tickercash.clerk.HistoricalDataClerk;

public class CMCHistoricalDataClerkTest {
	
	public static void main(String[] args) throws Exception{
		HistoricalDataClerk hdc = new CMCHistoricalDataClerk();
		hdc.getHistoricalTicks("bitcoin", "20130101", "20180304")
		.stream()
		.forEach(System.out::println);
	}

}
