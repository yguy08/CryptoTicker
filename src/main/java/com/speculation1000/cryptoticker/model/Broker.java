package com.speculation1000.cryptoticker.model;

public interface Broker {
   
    public void start() throws Exception;
    
    public void subscribe(String symbol) throws Exception;
    
    public void unsubscribe(String symbol) throws Exception;
    
    public void submitOrder(Order order) throws Exception;
    
    public void cancelAllOrders() throws Exception;
    
    public void cancelAllOrders(String symbol) throws Exception;
    
    public void reset() throws Exception;
    
}
