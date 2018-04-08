package com.tickercash.tapereader.window;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.tickercash.tapereader.DefaultTapeReader;
import com.tickercash.tapereader.tip.module.TapeReaderModule;

public class EmbeddedStarter {

    public static void main(String[] args) throws Exception {
        Injector injector = Guice.createInjector(new TapeReaderModule());
        DefaultTapeReader reader = injector.getInstance(DefaultTapeReader.class);
    }

}
