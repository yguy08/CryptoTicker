package com.tickercash.tapereader.tip.statement;

public class DefaultTipStatement implements TipStatement {
    
    @Override
    public String toString() {
        return "select * from Tick";    
    }

}
