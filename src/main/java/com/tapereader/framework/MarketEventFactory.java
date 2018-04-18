package com.tapereader.framework;

import com.lmax.disruptor.EventFactory;

@SuppressWarnings("rawtypes")
public class MarketEventFactory<T> implements EventFactory<MarketEvent<T>> {

    @Override
    public MarketEvent<T> newInstance() {
        return new MarketEvent();
    }

}
