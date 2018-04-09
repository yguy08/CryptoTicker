package com.tickercash.tapereader.ticker;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;

import com.tickercash.tapereader.framework.Transmitter;

public abstract class XQuoteBoy extends AbstractQuoteBoy {

    protected Exchange EXCHANGE;

    public XQuoteBoy(Transmitter transmitter, String exchangeName) {
    	super(transmitter);
        EXCHANGE = ExchangeFactory.INSTANCE.createExchange(exchangeName);
    }
}