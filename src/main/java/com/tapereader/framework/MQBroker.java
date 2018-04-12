package com.tapereader.framework;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.BrokerService;

public class MQBroker {
	
	public static void initDefaultMQBroker() throws Exception {
		try {
			// Test Connection
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
            connectionFactory.createConnection();
		}catch(Exception e) {
			BrokerService broker = new BrokerService();
            broker.addConnector("tcp://localhost:61616");
            broker.setDeleteAllMessagesOnStartup(true);
            broker.start();
		}
	}
	
}
