package com.tapereader.bucketshop;

import com.tapereader.gui.Displayable;

public enum BucketShopType implements Displayable {
    
    POLONIEX("Poloniex"),
    BINANCE("Binance"),
    GDAX("GDAX"),
    KUCOIN("Kucoin");
    
    private String displayName;
    
    BucketShopType(String displayName){
        this.displayName = displayName;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }
}
