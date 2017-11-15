package com.speculation1000.cryptoticker.disruptor;

import org.knowm.xchange.dto.marketdata.Ticker;

import com.lmax.disruptor.RingBuffer;
import com.speculation1000.cryptoticker.core.UniqueCurrentTimeMS;
import com.speculation1000.cryptoticker.model.TickVO;

public class TickEventProducer{
    
	private final RingBuffer<TickVO> ringBuffer;

    public TickEventProducer(RingBuffer<TickVO> ringBuffer){
        this.ringBuffer = ringBuffer;
    }

    public void onData(Ticker ticker){
        long sequence = ringBuffer.next();  // Grab the next sequence
        try{
        	TickVO event = ringBuffer.get(sequence); // Get the entry in the Disruptor for the sequence
            event.set(ticker.getCurrencyPair().toString(),UniqueCurrentTimeMS.uniqueCurrentTimeMS(),ticker.getLast().doubleValue(),
            		ticker.getBid().doubleValue(),ticker.getAsk().doubleValue(),ticker.getVolume().intValue());  // Fill with data
        }finally{
            ringBuffer.publish(sequence);
        }
    }
}
