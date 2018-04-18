package com.tapereader.framework;

import java.util.concurrent.Executors;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.tapereader.model.Tick;

@SuppressWarnings({"unchecked","rawtypes"})
public class DisruptorHolder<T> {
    
    private Disruptor<MarketEvent<T>> disruptor;
    
    private RingBuffer<MarketEvent<T>> ringBuffer;
    
    private MarketEventFactory<MarketEvent<T>> eventFactory = new MarketEventFactory<MarketEvent<T>>();
    
    public DisruptorHolder(){
        disruptor = new Disruptor(eventFactory, 1024, Executors.defaultThreadFactory(),
                ProducerType.SINGLE, new BlockingWaitStrategy());
        ringBuffer = disruptor.getRingBuffer();
    }
    
    public void handleEventsWith(EventHandler<MarketEvent<T>> handler){
        disruptor.handleEventsWith(handler);
    }
    
    public void start() throws Exception {
        disruptor.start();
    }
    
    public void translateTo(MarketEvent event, long sequence, Object data){
        event.set(data);
    }
    
    private final void onTick(MarketEvent tick, long sequence, boolean endOfBatch) throws Exception {
        System.out.println(tick.get().toString());
    }
    
    public void publishEvent(Object object){
        ringBuffer.publishEvent(this::translateTo, object);
    }
    
    public static void main(String[] args) throws Exception{
        DisruptorHolder<MarketEvent<Tick>> disruptor = new DisruptorHolder();
        disruptor.handleEventsWith(disruptor::onTick);
        disruptor.start();
        for(int i = 0;i < 10;i++){
            disruptor.publishEvent(new Tick("BTC","FAKE",1000L,1000.00));
        }
    }
}
