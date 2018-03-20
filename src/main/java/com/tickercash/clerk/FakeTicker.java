package com.tickercash.clerk;

import java.util.Random;

import com.tickercash.marketdata.Tick;
import com.tickercash.util.UniqueCurrentTimeMS;

public class FakeTicker extends LiveDataClerk {
    
    private Random random;
    
    public FakeTicker(){
        random = new Random();
    }

    @Override
    public void start() {
        disruptor.start();
        while(true){
            onTick(new Tick("FAKE/BTC",UniqueCurrentTimeMS.uniqueCurrentTimeMS(), random.nextDouble()));
            try{
                Thread.sleep(1000);
            }catch(Exception e){
                
            }
        }        
    }

}
