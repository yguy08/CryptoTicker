package com.tickercash.tapereader.tip.trend;

import com.tickercash.tapereader.framework.TipStatement;

public class DefaultTipStatement implements TipStatement {
    
    @Override
    public String toString() {
        return "select * from Tick";   
    }

}
