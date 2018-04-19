package com.tapereader.window;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.tapereader.TapeReader;
import com.tapereader.module.TestModule;

public class WindowTest {

    public static void main(String[] args) throws Exception {
        Injector injector = Guice.createInjector(new TestModule());
        TapeReader reader = injector.getInstance(TapeReader.class);
        reader.readTheTape();
    }

}
