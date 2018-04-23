package com.tapereader.framework;

public interface Receiver {

    void initialize(Object object) throws Exception;

    void read() throws Exception;

    void detach() throws Exception;

}
