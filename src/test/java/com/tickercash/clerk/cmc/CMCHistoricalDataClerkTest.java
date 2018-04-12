package com.tickercash.clerk.cmc;

import static org.junit.Assert.assertNotNull;

import java.time.LocalDateTime;
import org.junit.Test;

import com.tapereader.framework.HistoricalTicker;
import com.tapereader.ticker.cmc.CMCHistoricalTicker;

public class CMCHistoricalDataClerkTest {
    
    private static LocalDateTime testDate = LocalDateTime.of(2018,3,5,8,55);

    @Test
    public void testBitcoinHistorical() throws Exception {
        HistoricalTicker hdc = new CMCHistoricalTicker();
        hdc.getHistoricalTicks("Bitcoin", testDate.minusYears(5), testDate)
           .stream()
           .forEach(t -> assertNotNull(t));
    }
    
}
