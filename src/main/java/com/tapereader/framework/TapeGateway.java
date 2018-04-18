package com.tapereader.framework;

public interface TapeGateway {

    void initialize(Object object) throws Exception;

    void read() throws Exception;

    void detach() throws Exception;

}
