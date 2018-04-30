package com.tapereader.framework;

public interface Ticker {

    void start() throws Exception;

    void stop() throws Exception;

    void subscribe(String symbol);

    void subscribe(String... symbol);

}
