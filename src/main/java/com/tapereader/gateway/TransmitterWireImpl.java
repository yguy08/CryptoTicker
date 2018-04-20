package com.tapereader.gateway;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.BrokerService;

import com.google.inject.Inject;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.tapereader.config.ActiveMQBrokerURL;
import com.tapereader.config.TopicName;
import com.tapereader.framework.DisruptorClerk;
import com.tapereader.framework.Event;
import com.tapereader.framework.MarketEvent;
import com.tapereader.framework.Transmitter;

import net.openhft.chronicle.bytes.Bytes;
import net.openhft.chronicle.core.pool.ClassAliasPool;
import net.openhft.chronicle.wire.TextWire;
import net.openhft.chronicle.wire.Wire;

public class TransmitterWireImpl implements Transmitter {
    
    private final String BROKER_URL;
    private final String UPDATE_TOPIC_NAME;
    private Connection connection;
    private Session session;
    private MessageProducer updateProducer;
    
    private final Disruptor<Event> disruptor;
    
    private final RingBuffer<Event> ringBuffer;
    
    @Inject
    private TransmitterWireImpl(@ActiveMQBrokerURL String brokerURL, @TopicName String topicName) throws Exception {
        BROKER_URL = brokerURL;
        UPDATE_TOPIC_NAME = topicName;
        initialize();
        disruptor = DisruptorClerk.newMarketEventDisruptor();
        ringBuffer = disruptor.getRingBuffer();
        disruptor.handleEventsWith(this::onTick);
        disruptor.start();
    }
    
    private void initialize() throws Exception {
        try{
            BrokerService broker = new BrokerService();
            broker.setPersistent(false);
            broker.setUseJmx(false);
            broker.addConnector(BROKER_URL);
            broker.start();
        }catch (Exception e){
            
        }
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(BROKER_URL);
        connection = connectionFactory.createConnection();
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination updateTopic = session.createTopic(UPDATE_TOPIC_NAME);
        updateProducer = session.createProducer(updateTopic);

        connection.start();
        ClassAliasPool.CLASS_ALIASES.addAlias(MarketEvent.class);
    }
    
    private static final Wire wire = new TextWire(Bytes.elasticByteBuffer());

    @Override
    public void transmit(Event event) throws Exception {
        event.writeMarshallable(wire);
        ringBuffer.publishEvent(this::translateTo, wire);
    }
    
    private void onTick(Event event, long sequence, boolean endOfBatch) throws Exception {
        TextMessage message = session.createTextMessage(event.toString());
        updateProducer.send(message);
    }
    
    private final void translateTo(Event event, long sequence, Wire wire){
        event.readMarshallable(wire);
    }

    @Override
    public void endTransmission() throws Exception {
        if (connection != null) {
            connection.stop();
            connection.close();
        }
    }
}
