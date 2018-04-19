package com.tapereader.module;

import com.google.inject.AbstractModule;
import com.tapereader.TapeReader;
import com.tapereader.config.ActiveMQBrokerURL;
import com.tapereader.config.TopicName;
import com.tapereader.framework.TapeGateway;
import com.tapereader.framework.Ticker;
import com.tapereader.framework.Transmitter;
import com.tapereader.gateway.TapeGatewayImpl;
import com.tapereader.gateway.TransmitterImpl;
import com.tapereader.listener.OrderEventListener;
import com.tapereader.listener.TickEventListener;
import com.tapereader.ticker.FakeTicker;

public class TestModule extends AbstractModule {
	
	String mqURL = "vm://localhost";
	String topicName = "global.test";
	
	@Override
	public void configure(){
        bind(Ticker.class).to(FakeTicker.class);
        bind(Transmitter.class).to(TransmitterImpl.class);
        bind(TapeGateway.class).to(TapeGatewayImpl.class);
        bind(TickEventListener.class).to(TapeReader.class);
        bind(OrderEventListener.class).to(TapeReader.class);
        bindConstant().annotatedWith(ActiveMQBrokerURL.class).to(mqURL);
        bindConstant().annotatedWith(TopicName.class).to(topicName);

	}

}
