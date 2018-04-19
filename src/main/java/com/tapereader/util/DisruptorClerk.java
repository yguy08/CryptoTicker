package com.tapereader.util;

import java.util.concurrent.Executors;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.tapereader.framework.MarketEvent;
import com.tapereader.model.Tick;

public class DisruptorClerk {

    public static final Disruptor<Tick> createDefaultMarketEventDisruptor(){
        return new Disruptor<Tick>(Tick::new, 1024, Executors.defaultThreadFactory(),
                ProducerType.SINGLE, new BlockingWaitStrategy());
    }
    
    public static final Disruptor<String> createStringDisruptor(){
        return new Disruptor<String>(String::new, 1024, Executors.defaultThreadFactory(), ProducerType.SINGLE, new BlockingWaitStrategy());
    }
    
    public static final Disruptor<MarketEvent> newMarketEventDisruptor(){
        return new Disruptor<MarketEvent>(MarketEvent::new, 1024, Executors.defaultThreadFactory(),
                ProducerType.SINGLE, new BlockingWaitStrategy());
    }

}
