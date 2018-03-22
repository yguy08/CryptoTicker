package com.tickercash.clerk;

import java.util.concurrent.Executors;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.tickercash.event.MarketDataEvent;

public class DisruptorClerk {

    public static final Disruptor<MarketDataEvent> createDefaultMarketEventDisruptor(){
        return new Disruptor<MarketDataEvent>(MarketDataEvent.MARKET_EVENT_FACTORY, 1024, Executors.defaultThreadFactory(),
                ProducerType.SINGLE, new BlockingWaitStrategy());
    }

}
