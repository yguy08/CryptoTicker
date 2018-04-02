package com.tickercash.tapereader.config;

import com.tickercash.tapereader.clerk.QuoteBoyType;

public class ServerConfig {
    
    private QuoteBoyType quoteBoy;
    
    private int quoteThrottle;
    
    private String wireURL;
    
    public QuoteBoyType getQuoteBoy() {
        return quoteBoy;
    }

    public void setQuoteBoy(QuoteBoyType quoteBoyType) {
        this.quoteBoy = quoteBoyType;
    }

    public int getQuoteThrottle(){
        return quoteThrottle;
    }

    public void setQuoteThrottle(int seconds){
        this.quoteThrottle = seconds;
    }
    
    public String getWireURL() {
        return wireURL;
    }

    public void setWireURL(String wireURL) {
        this.wireURL = wireURL;
    }
    
    @Override
    public String toString(){
        return new StringBuilder()
                .append(String.format("Quote Boy: %s\n", quoteBoy.toString()))
                .append(String.format("Quote Throttle: %d\n", quoteThrottle))
                .append(String.format("Wire URL: %d\n", wireURL))
                .toString();
    }
}
