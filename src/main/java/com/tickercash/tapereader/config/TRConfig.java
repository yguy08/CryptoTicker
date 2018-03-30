package com.tickercash.tapereader.config;

import com.tickercash.tapereader.clerk.QuoteBoyType;

public class TRConfig {
    
    private QuoteBoyType quoteBoy;
    
    private Boolean preFeed;
    
    public void setQuoteBoy(QuoteBoyType quoteBoy) {
        this.quoteBoy = quoteBoy;
    }
    
    public QuoteBoyType getQuoteBoy() {
        return quoteBoy;
    }

    public Boolean getPreFeed() {
        return preFeed;
    }

    public void setPreFeed(Boolean preFeed) {
        this.preFeed = preFeed;
    }
}
