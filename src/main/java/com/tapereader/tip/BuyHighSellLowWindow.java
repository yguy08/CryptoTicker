package com.tapereader.tip;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.tickercash.tapereader.TapeReader;

public class BuyHighSellLowWindow {

    public static void main(String[] args) throws Exception {
        Injector injector = Guice.createInjector(new BuyHighSellLowModule());
        TapeReader reader = injector.getInstance(BuyHighSellLow.class);
        reader.readTheTape();
    }

}
