package com.tickercash.app;

import org.knowm.xchange.binance.BinanceExchange;

import com.tickercash.model.Tick;
import com.tickercash.tapereader.TapeReaderClerk;
import com.tickercash.tapereader.TipEngine;
import com.tickercash.tapereader.XchangeTicker;

public class BinanceTicker extends TapeReaderClerk {
	
	String epl = "select symbol,timestamp,last from Tick";
	
	public BinanceTicker(){
		setName("BinanceTicker");
		setTicker(new XchangeTicker(BinanceExchange.class.getName()));
		setTipEngine(new TipEngine(epl));
		getTicker().subscribe("BTC/USDT");
		getTicker().subscribe("ETH/BTC");
		getTicker().subscribe("XRP/BTC");
		readTheTape();
	}
	
	public static void main(String[] args){
		new BinanceTicker();
	}
	
	@Override
	public void onTick(Tick tick){
		System.out.println(tick);
	}

}
