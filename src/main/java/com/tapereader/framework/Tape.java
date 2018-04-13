package com.tapereader.framework;

public interface Tape {
    void write(Event event) throws Exception;
    void read(Object object) throws Exception;
}
