package com.tickercash.marketdata;

import java.util.Random;

import com.tickercash.util.UniqueCurrentTimeMS;

public class FakeTick extends Tick {
    
    private static final Random random = new Random();
    
    
    public FakeTick(){
        super("BTC/USD", "Fake", UniqueCurrentTimeMS.uniqueCurrentTimeMS(), random.nextDouble());
    }

}
