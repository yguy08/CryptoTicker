package com.tapereader.wire;

import javax.jms.JMSException;
import javax.naming.NamingException;

public class Subject {
    
    private SubjectGateway gateway;
    
    public Subject() throws JMSException, NamingException {
        gateway = SubjectGateway.newGateway();
    }
    
    public void run() throws Exception {
        while(true){
            gateway.notify("Subject!");
            Thread.sleep(1000);
        }
    }
    
    public static void main(String[] args) throws JMSException, NamingException, Exception {
        new Subject().run();
    }

}
