package com.tapereader.module;

import com.google.inject.AbstractModule;
import com.tapereader.annotation.ActiveMQBrokerURL;
import com.tapereader.annotation.CsvFile;
import com.tapereader.annotation.ModuleName;
import com.tapereader.annotation.TopicName;
import com.tapereader.dao.TipDao;
import com.tapereader.dao.TipDaoImpl;
import com.tapereader.framework.DefaultEngine;
import com.tapereader.framework.DefaultReceiver;
import com.tapereader.framework.DefaultTransmitter;
import com.tapereader.framework.Engine;
import com.tapereader.framework.Receiver;
import com.tapereader.framework.Ticker;
import com.tapereader.framework.Transmitter;
import com.tapereader.ticker.CsvTicker;

public class FakeTestModule extends AbstractModule {
	
    private String module = "BuyHighSellLow.epl";
	
	@Override
	public void configure(){
        bind(Ticker.class).to(CsvTicker.class);
        bind(Transmitter.class).to(DefaultTransmitter.class);
        bind(Receiver.class).to(DefaultReceiver.class);
        bind(Engine.class).to(DefaultEngine.class);
        bind(TipDao.class).to(TipDaoImpl.class);
        bindConstant().annotatedWith(ModuleName.class).to(module);
        bindConstant().annotatedWith(ActiveMQBrokerURL.class).to("vm://localhost");
        bindConstant().annotatedWith(TopicName.class).to("global.update");
        bindConstant().annotatedWith(CsvFile.class).to("src/main/resources/btcUSD.csv");
	}

}
