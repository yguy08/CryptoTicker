package com.tickercash.enums;

public enum MarketDataSource implements Displayable {
    
    POLONIEX("Poloniex"),
    BINANCE("Binance"),
    CMC("CMC"),
    GDAX("GDAX"),
    KUCOIN("Kucoin"),
    FAKE("Fake");
	
    private String displayName;
    
    MarketDataSource(String displayName){
        this.displayName = displayName;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

}
