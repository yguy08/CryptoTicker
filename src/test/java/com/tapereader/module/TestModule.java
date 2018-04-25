package com.tapereader.module;

import com.google.inject.AbstractModule;
import com.tapereader.TapeReader;
import com.tapereader.config.ActiveMQBrokerURL;
import com.tapereader.config.TopicName;
import com.tapereader.framework.DefaultReceiver;
import com.tapereader.framework.DefaultTransmitter;
import com.tapereader.framework.OrderEventListener;
import com.tapereader.framework.Receiver;
import com.tapereader.framework.TickEventListener;
import com.tapereader.framework.Ticker;
import com.tapereader.framework.Transmitter;
import com.tapereader.ticker.FakeTicker;

public class TestModule extends AbstractModule {
	
	String mqURL = "vm://localhost";
	String topicName = "global.test";
	
	@Override
	public void configure(){
        bind(Ticker.class).to(FakeTicker.class);
        bind(Transmitter.class).to(DefaultTransmitter.class);
        bind(Receiver.class).to(DefaultReceiver.class);
        bind(TickEventListener.class).to(TapeReader.class);
        bind(OrderEventListener.class).to(TapeReader.class);
        bindConstant().annotatedWith(ActiveMQBrokerURL.class).to(mqURL);
        bindConstant().annotatedWith(TopicName.class).to(topicName);

	}

}
