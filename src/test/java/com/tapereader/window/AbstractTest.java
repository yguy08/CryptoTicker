package com.tapereader.window;

import java.io.IOException;
import java.util.Properties;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.poloniex.PoloniexExchange;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsAll;

import static com.tapereader.util.Cryptor.getEncryptableProperties;

public class AbstractTest {

    public static void main(String[] args) throws IOException {
        Properties props = getEncryptableProperties("POLO_PW", "poloniex.properties");
        String key = props.getProperty("key");
        String sec = props.getProperty("secret");
        Exchange EXCHANGE = ExchangeFactory.INSTANCE.createExchange(PoloniexExchange.class.getName(), key, sec);
        TradeService trade = EXCHANGE.getTradeService();
        UserTrades trades = trade.getTradeHistory(new TradeHistoryParamsAll());
        for(Trade ut : trades.getTrades()) {
            System.out.println(ut);
        }
    }

}
