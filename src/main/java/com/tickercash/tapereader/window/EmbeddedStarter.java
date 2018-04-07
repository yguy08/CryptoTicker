package com.tickercash.tapereader.window;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.tickercash.tapereader.TapeReader;
import com.tickercash.tapereader.TapeReaderModule;

public class EmbeddedStarter {

    public static void main(String[] args) throws Exception {
        Injector injector = Guice.createInjector(new TapeReaderModule());
        TapeReader reader = injector.getInstance(TapeReader.class);
        reader.readTheTape();
    }

}
