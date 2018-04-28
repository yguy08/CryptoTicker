package com.tapereader.ticker;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;

import com.tapereader.framework.Transmitter;

public abstract class XTicker extends AbstractTicker {

    protected Exchange EXCHANGE;

    public XTicker(Transmitter transmitter, String exchangeName) {
    	super(transmitter);
        EXCHANGE = ExchangeFactory.INSTANCE.createExchange(exchangeName);
    }
}