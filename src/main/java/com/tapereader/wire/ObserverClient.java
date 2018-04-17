package com.tapereader.wire;

import javax.jms.JMSException;
import javax.naming.NamingException;

import com.google.common.eventbus.Subscribe;

public class ObserverClient {
    
    private ObserverGateway gateway;
    
    public ObserverClient() throws JMSException, NamingException{
        gateway = ObserverGateway.newGateway(this);
        gateway.attach();
    }

    public static void main(String[] args) throws JMSException, NamingException {
        new ObserverClient();
    }

    @Subscribe
    public void update(Object arg) {
        System.out.println(arg);
    }

}
