package com.tickercash.enums;

public enum Broker implements Displayable {
    
    POLONIEX("Poloniex"),
    BINANCE("Binance"),
    GDAX("GDAX"),
    KUCOIN("Kucoin");
    
    private String displayName;
    
    Broker(String displayName){
        this.displayName = displayName;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }
}
