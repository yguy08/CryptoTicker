package com.tickercash.tapereader.core;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Config {
    
    private static Properties properties;
    
    public static void init(final String resourceName) throws IOException {
        properties = new Properties();
        properties.load(new FileInputStream(resourceName));        
    }
    
    public static String getStringProperty(String property){
        return properties.getProperty(property);
    }
    
    public static int getIntProperty(String property){
        return Integer.parseInt(properties.getProperty(property));
    }
    
    public static String getTape(){
        return properties.getProperty("tape");
    }
    
    public static String getXchangeExchange(){
        return properties.getProperty("xchange.exchange");
    }
    
    public static String getSymbols(){
        return properties.getProperty("symbols");
    }
    
    public static String getEventHandlers(){
        return properties.getProperty("event.handler");
    }
    
    public static String getCsvFile(){
        return properties.getProperty("csv.file");
    }
    
    public static int getThrottle(){
        return Integer.parseInt(properties.getProperty("throttle"));
    }
    
}
