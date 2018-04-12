package com.tickercash.tapereader.window;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.tapereader.tip.BuyHighSellLowModule;
import com.tickercash.tapereader.TapeReader;

public class DefaultStarter {

    public static void main(String[] args) throws Exception {
        Injector injector = Guice.createInjector(new BuyHighSellLowModule());
        TapeReader reader = injector.getInstance(TapeReader.class);
        reader.readTheTape();
    }

}
