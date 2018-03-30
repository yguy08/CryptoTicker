package com.tickercash.clerk;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.lmax.disruptor.EventHandler;
import com.tickercash.tapereader.clerk.FakeQuoteBoy;
import com.tickercash.tapereader.clerk.QuoteBoy;
import com.tickercash.tapereader.marketdata.Tick;

@SuppressWarnings("unchecked")
public class FakeQuoteBoyTest {
    @Test
    public void testFakeQuoteBoy() throws Exception {
        QuoteBoy quoteBoy = new FakeQuoteBoy();
        
        quoteBoy.addHandler(new EventHandler<Tick>() {
            @Override
            public void onEvent(Tick event, long sequence, boolean endOfBatch) throws Exception {
                assertNotNull(event);
            }
        });
        
        Thread t = new Thread(() -> {
           try {
               quoteBoy.start();
           } catch (Exception e) {
               e.printStackTrace();
            }
        });
        t.start();
        Thread.sleep(1000);
        quoteBoy.stop();
    }
}
