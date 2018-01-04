package com.tickercash.tapereader;

import java.util.Random;

public class FakeTicker extends Ticker {
    
    private Random random;
    
    public FakeTicker(){
        random = new Random();
    }

    @Override
    public void start() {
        disruptor.start();
        while(true){
            onTick("FAKE/BTC",UniqueCurrentTimeMS.uniqueCurrentTimeMS(), random.nextDouble());
            try{
                Thread.sleep(1000);
            }catch(Exception e){
                
            }
        }        
    }

}
