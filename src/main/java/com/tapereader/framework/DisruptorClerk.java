package com.tapereader.framework;

import java.util.concurrent.Executors;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.tapereader.model.Tick;

public class DisruptorClerk {

    public static final Disruptor<Tick> newTickDisruptor(){
        return new Disruptor<Tick>(Tick::new, 1024, Executors.defaultThreadFactory(),
                ProducerType.SINGLE, new BlockingWaitStrategy());
    }

}
