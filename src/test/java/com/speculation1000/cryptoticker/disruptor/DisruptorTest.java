package com.speculation1000.cryptoticker.disruptor;

import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.poloniex.PoloniexExchange;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.speculation1000.cryptoticker.model.TickVO;

public class DisruptorTest {
	
	private static final Exchange POLONIEX = ExchangeFactory.INSTANCE.createExchange(PoloniexExchange.class.getName());

	public static void main(String[] args) throws InterruptedException, SQLException, 
				NotAvailableFromExchangeException, NotYetImplementedForExchangeException, ExchangeException, IOException {
		
        // Executor that will be used to construct new threads for consumers
        ThreadFactory threadFactory = Executors.defaultThreadFactory();

        // The factory for the event
        TickEventFactory factory = new TickEventFactory();

        // Specify the size of the ring buffer, must be power of 2.
        int bufferSize = 1024;

        // Construct the Disruptor
        Disruptor<TickVO> disruptor = new Disruptor<>(factory, bufferSize, threadFactory);

        // Connect the handler
        disruptor.handleEventsWith(new TickEventHandler("main"));

        // Start the Disruptor, starts all threads running
        disruptor.start();

        // Get the ring buffer from the Disruptor to be used for publishing.
        RingBuffer<TickVO> ringBuffer = disruptor.getRingBuffer();

        TickEventProducer producer = new TickEventProducer(ringBuffer);
        
        threadFactory.newThread(new HelloWorldConsumer()).start();
        
        while(true){
        	Ticker ticker = POLONIEX.getMarketDataService().getTicker(new CurrencyPair(Currency.BTC, Currency.USDT));
        	producer.onData(ticker);
            Thread.sleep(1000);
        }

	}
	
    public static class HelloWorldConsumer implements Runnable, ExceptionListener {
        public void run() {
            try {
 
                // Create a ConnectionFactory
                ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("vm://localhost");
 
                // Create a Connection
                Connection connection = connectionFactory.createConnection();
                connection.start();
 
                connection.setExceptionListener(this);
 
                // Create a Session
                Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
 
                // Create the destination (Topic or Queue)
                Destination destination = session.createQueue("TEST.FOO");
 
                // Create a MessageConsumer from the Session to the Topic or Queue
                MessageConsumer consumer = session.createConsumer(destination);
 
                while(true){

                    // Wait for a message
                    Message message = consumer.receive(0);
     
                    if (message instanceof TextMessage) {
                        TextMessage textMessage = (TextMessage) message;
                        String text = textMessage.getText();
                        System.out.println("Received: " + text);
                    } else {
                        System.out.println("Received: " + message);
                    }
                }
            } catch (Exception e) {
                System.out.println("Caught: " + e);
                e.printStackTrace();
            }
        }
 
        public synchronized void onException(JMSException ex) {
            System.out.println("JMS Exception occured.  Shutting down client.");
        }

    }
    
}
