package com.tapereader.window;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.tapereader.TapeReader;
import com.tapereader.tip.BuyHighSellLowModule;

public class DefaultStarter {

    public static void main(String[] args) throws Exception {
        Injector injector = Guice.createInjector(new BuyHighSellLowModule());
        TapeReader reader = injector.getInstance(TapeReader.class);
        reader.readTheTape();
    }

}
