package com.tickercash.tapereader.clerk;

import com.tickercash.tapereader.gui.Displayable;

public enum QuoteBoyType implements Displayable {
    
    POLONIEX("Poloniex"),
    BINANCE("Binance"),
    CMC("CMC"),
    GDAX("GDAX"),
    KUCOIN("Kucoin"),
    FAKE("Fake"),
    CSV("Csv");
	
    private String displayName;
    
    QuoteBoyType(String displayName){
        this.displayName = displayName;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

}
