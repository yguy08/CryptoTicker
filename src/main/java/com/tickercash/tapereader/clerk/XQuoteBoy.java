package com.tickercash.tapereader.clerk;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;

import com.tickercash.tapereader.wire.Transmitter;

public abstract class XQuoteBoy extends AbstractQuoteBoy {

    protected Exchange EXCHANGE;

    public XQuoteBoy(Transmitter transmitter, String exchangeName) {
    	super(transmitter);
        EXCHANGE = ExchangeFactory.INSTANCE.createExchange(exchangeName);
    }
}