package com.tapereader.framework;

public class MarketEvent<T> {

    private T value;
    
    public T get() {
        return value;
    }
    
    public void set(T value){
        this.value = value;
    }
    
}
