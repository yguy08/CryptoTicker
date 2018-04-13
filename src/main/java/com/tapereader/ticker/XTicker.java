package com.tapereader.ticker;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;

import com.tapereader.framework.Tape;

public abstract class XTicker extends AbstractTicker {

    protected Exchange EXCHANGE;

    public XTicker(Tape tape, String exchangeName) {
    	super(tape);
        EXCHANGE = ExchangeFactory.INSTANCE.createExchange(exchangeName);
    }
}