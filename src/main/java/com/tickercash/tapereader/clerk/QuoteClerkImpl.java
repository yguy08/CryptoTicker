package com.tickercash.tapereader.clerk;

import com.tickercash.tapereader.config.Config;

public class QuoteClerkImpl implements QuoteClerk {
	
	private Config config;
	private QuoteBoy quoteBoy;
	
    public QuoteClerkImpl(Config config, QuoteBoy quoteBoy){
        this.config = config;
        this.quoteBoy = quoteBoy;
    }
    
    @Override
    public void requestCurrentQuotes() {
    	
    }

}
