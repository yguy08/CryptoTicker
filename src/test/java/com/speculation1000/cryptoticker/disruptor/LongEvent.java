package com.speculation1000.cryptoticker.disruptor;

public class LongEvent {
    private long value;

    public void set(long value){
        this.value = value;
    }
    
    @Override
    public String toString(){
    	return "Value: " + value;
    }
}