package com.tickercash.marketdata;

import org.knowm.xchange.coinmarketcap.dto.marketdata.CoinMarketCapTicker;

import com.tickercash.util.NumberFormatPatterns;

public class CMCTicker extends MarketEvent {
	
	private CoinMarketCapTicker cmcTicker;
	
	private StringBuilder sb;

	public CMCTicker(CoinMarketCapTicker cmcTicker) {
		this.cmcTicker = cmcTicker;
	}
	
	@Override
	public String toString(){
		sb = new StringBuilder();
		sb.append(cmcTicker.getName().toUpperCase()+":");
		//sb.append(NumberFormatPatterns.SAT_FORMAT.format(cmcTicker.getPriceBTC().doubleValue())+" ");
		sb.append(NumberFormatPatterns.SAT_FORMAT.format(cmcTicker.getPriceUSD().doubleValue())+" ");
		//sb.append(NumberFormatPatterns.SAT_FORMAT.format(cmcTicker.getVolume24hUSD().doubleValue())+" ");
		return sb.toString();
	}

}
