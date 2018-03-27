package com.tickercash.tapereader.clerk;

import com.tickercash.tapereader.event.TickEventListener;
import com.tickercash.tapereader.model.Tick;

public class TipClerk implements TickEventListener {
    
    public TipClerk(){
    	
    }
    
    public String getTipName() {
    	return "GLOBAL";
    }

	@Override
	public void onTick(Tick tick) {
		// send order
		System.out.println("SENDING ORDER: "+tick.toString());
	}
}
