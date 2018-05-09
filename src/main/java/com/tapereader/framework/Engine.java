package com.tapereader.framework;

public interface Engine {
    
    void sendEvent(Object event) throws Exception;
    
    void addSubscriber(String stmtName, Object object, String methodName) throws Exception;
    
    void loadStatements(Object obj, String source);
}
