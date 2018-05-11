package com.tapereader.util;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.poloniex.PoloniexExchange;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsAll;

import static com.tapereader.util.Cryptor.getEncryptableProperties;

public class TradeHist {

    public static void main(String[] args) throws Exception {
        Properties props = getEncryptableProperties(System.getenv("POLO_PW"), "poloniex.properties");
        String key = props.getProperty("key");
        String sec = props.getProperty("secret");
        Exchange EXCHANGE = ExchangeFactory.INSTANCE.createExchange(PoloniexExchange.class.getName(), key, sec);
        TradeService trade = EXCHANGE.getTradeService();
        TradeHistoryParamsAll params = new TradeHistoryParamsAll();
        params.setStartTime(new Date(LocalDateTime.MIN.toEpochSecond(ZoneOffset.UTC)));
        params.setEndTime(new Date(LocalDateTime.MAX.toEpochSecond(ZoneOffset.UTC)));
        UserTrades trades = trade.getTradeHistory(params);
        List<Trade> list = trades.getTrades();
        for(Trade ut : list) {
            System.out.println(ut);
            Thread.sleep(250);
        }
    }

}
