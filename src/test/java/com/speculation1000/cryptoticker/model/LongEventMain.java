package com.speculation1000.cryptoticker.model;

import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.RingBuffer;
import java.nio.ByteBuffer;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class LongEventMain {
	
    public static void handleEvent(LongEvent event, long sequence, boolean endOfBatch) {
        System.out.println(event);
    }

    public static void translate(LongEvent event, long sequence, ByteBuffer buffer) {
        event.set(buffer.getLong(0));
    }

    public static void main(String[] args) throws Exception {
        // Executor that will be used to construct new threads for consumers
        Executor executor = Executors.newCachedThreadPool();

        // Specify the size of the ring buffer, must be power of 2.
        int bufferSize = 1024;

        // Construct the Disruptor
        Disruptor<LongEvent> disruptor = new Disruptor<>(LongEvent::new, bufferSize, executor);

        // Connect the handler
        disruptor.handleEventsWith(LongEventMain::handleEvent);

        // Start the Disruptor, starts all threads running
        disruptor.start();

        // Get the ring buffer from the Disruptor to be used for publishing.
        RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();

        ByteBuffer bb = ByteBuffer.allocate(8);
        for (long l = 0; true; l++){
            bb.putLong(0, 1510872183L);
            ringBuffer.publishEvent(LongEventMain::translate, bb);
            Thread.sleep(1000);
        }
    }
}
