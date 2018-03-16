package com.tickercash.enums;

public enum MarketDataSource implements Displayable {
    
    POLONIEX("Poloniex"),
    BINANCE("Binance"),
    GDAX("GDAX"),
    KUCOIN("Kucoin"),
    CMC("CMC");
    
    private String displayName;
    
    MarketDataSource(String displayName){
        this.displayName = displayName;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

}
