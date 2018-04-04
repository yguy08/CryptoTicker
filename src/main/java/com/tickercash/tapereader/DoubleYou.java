package com.tickercash.tapereader;

import com.espertech.esper.client.EventBean;
import com.tickercash.tapereader.marketdata.Tick;

public class DoubleYou extends TapeReader {
    
    @Override
    public void readTheTape() {
        String stmt = "@Name(SelectAll) select * from Tick";
        getTipEngine().addStatement(stmt);
        addTipListener(this::update);
        getReceiver().startReceiving();
    }
    
    @Override
    public void update(EventBean[] newTick, EventBean[] oldTick){
        String symbol = (String) newTick[0].get("symbol");
        System.out.println(symbol);
    }
    
    @Override
    public void onTick(Tick tick){
        System.out.println(tick.getLast() + " " + tick.getSymbol());
    }
}