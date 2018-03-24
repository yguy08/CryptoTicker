package com.tickercash.tapereader.clerk;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;

public abstract class XQuoteBoy extends QuoteBoy {

    protected Exchange EXCHANGE;

    public XQuoteBoy(String exchangeName) {
        EXCHANGE = ExchangeFactory.INSTANCE.createExchange(exchangeName);
    }
}