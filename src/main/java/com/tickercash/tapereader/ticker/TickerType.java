package com.tickercash.tapereader.ticker;

import com.tickercash.tapereader.gui.Displayable;

public enum TickerType implements Displayable {
    
    POLONIEX("Poloniex"),
    BINANCE("Binance"),
    CMC("CMC"),
    GDAX("GDAX"),
    KUCOIN("Kucoin"),
    FAKE("Fake"),
    CSV("Csv");
	
    private String displayName;
    
    TickerType(String displayName){
        this.displayName = displayName;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

}
