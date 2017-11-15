package com.speculation1000.cryptoticker.disruptor;

import com.lmax.disruptor.EventFactory;
import com.speculation1000.cryptoticker.model.TickVO;

public class TickEventFactory implements EventFactory<TickVO> {
    @Override
	public TickVO newInstance(){
        return new TickVO();
    }
}
