package com.tickercash.clerk;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;

public abstract class XchangeTicker extends QuoteBoy {

    protected Exchange EXCHANGE;

    public XchangeTicker(String exchangeName) {
        EXCHANGE = ExchangeFactory.INSTANCE.createExchange(exchangeName);
    }
}