package com.tapereader.wire;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.NamingException;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.BrokerService;

public class SubjectGateway {
    public static final String UPDATE_TOPIC_NAME = "jms/Update";
    private Connection connection;
    private Session session;
    private MessageProducer updateProducer;

    protected SubjectGateway() {
        
    }

    public static SubjectGateway newGateway() throws JMSException, NamingException {
        SubjectGateway gateway = new SubjectGateway();
        gateway.initialize();
        return gateway;
    }

    protected void initialize() throws JMSException, NamingException {
        try{
            BrokerService broker = new BrokerService();
            broker.setPersistent(false);
            broker.setUseJmx(false);
            broker.addConnector("tcp://localhost:61616");
            broker.start();
        }catch (Exception e){
            
        }
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        connection = connectionFactory.createConnection();
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination updateTopic = session.createTopic(UPDATE_TOPIC_NAME);
        updateProducer = session.createProducer(updateTopic);

        connection.start();
    }

    public void notify(String state) throws JMSException {
        TextMessage message = session.createTextMessage(state);
        updateProducer.send(message);
    }

    public void release() throws JMSException {
        if (connection != null) {
            connection.stop();
            connection.close();
        }
    }
}
