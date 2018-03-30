package com.tickercash.clerk.cmc;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.lmax.disruptor.EventHandler;
import com.tickercash.tapereader.clerk.QuoteBoy;
import com.tickercash.tapereader.clerk.cmc.CMCQuoteBoy;
import com.tickercash.tapereader.marketdata.Tick;

@SuppressWarnings("unchecked")
public class CMCQuoteBoyTest {

    @Test
    public void testCMCQuoteBoy() throws Exception {
        QuoteBoy quoteBoy = new CMCQuoteBoy();
        
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
