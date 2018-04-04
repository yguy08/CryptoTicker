package com.tickercash.tapereader;

import com.tickercash.tapereader.clerk.QuoteBoy;
import com.tickercash.tapereader.config.Config;
import com.tickercash.tapereader.wire.Transmitter;

public class QuoteServer {
    
    private Config config;
    
    private QuoteBoy quoteBoy;
    
    private String brokerURL;

    public Config getConfig() {
        return config;
    }

    public void setConfig(Config config) {
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
    
    public void init() throws Exception {
        setQuoteBoy(QuoteBoy.createQuoteBoy(config.getQuoteBoyType()));
        getQuoteBoy().addHandler(new Transmitter(config.getQuoteBoyType().toString()));
    }
    
    public void start() throws Exception {
    	getQuoteBoy().start();
    }
    
}
