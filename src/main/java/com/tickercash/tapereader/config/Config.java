package com.tickercash.tapereader.config;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.yaml.snakeyaml.Yaml;

import com.tickercash.tapereader.clerk.QuoteBoyType;

public class Config {
    
    private QuoteBoyType quoteBoy;
    
    private int quoteThrottle;
    
    private String wireURL;
    
    private Boolean preFeed;
    
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
    
	public Boolean getPreFeed() {
		return preFeed;
	}

	public void setPreFeed(Boolean preFeed) {
		this.preFeed = preFeed;
	}
	
	public static Config loadConfig(String path) throws IOException{
		Yaml yaml = new Yaml();
        try(InputStream in = Files.newInputStream(Paths.get(path))) {
            Config config = yaml.loadAs(in, Config.class);
            return config;
        }
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
