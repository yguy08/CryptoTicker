package com.tickercash.tapereader;

import com.tickercash.tapereader.clerk.QuoteBoy;
import com.tickercash.tapereader.config.ServerConfig;

public class QuoteServer {
    
    private ServerConfig config;
    
    private QuoteBoy quoteBoy;
    
    private String brokerURL;

    public ServerConfig getConfig() {
        return config;
    }

    public void setConfig(ServerConfig config) {
        this.config = config;
    }

    public QuoteBoy getQuoteBoy() {
        return quoteBoy;
    }

    public void setQuoteBoy(QuoteBoy quoteBoy) {
        this.quoteBoy = quoteBoy;
    }

    public String getBrokerURL() {
        return brokerURL;
    }

    public void setBrokerURL(String brokerURL) {
        this.brokerURL = brokerURL;
    }
}
