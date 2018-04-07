package com.tickercash.tapereader.tape;

import com.tickercash.tapereader.marketdata.Tick;
import com.tickercash.tapereader.tip.TipListener;

public interface Tape {
    
    void write(Tick tick) throws Exception;
    
    void read(TipListener tip) throws Exception;
}
