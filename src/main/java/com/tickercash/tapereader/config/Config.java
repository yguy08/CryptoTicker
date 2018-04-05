package com.tickercash.tapereader.config;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.yaml.snakeyaml.Yaml;

import com.tickercash.tapereader.clerk.QuoteBoyType;

public class Config {
    
    private QuoteBoyType quoteBoyType;
    
    private int quoteThrottle;
    
    private String wireURL;
    
    private Boolean preFeed;
    
    private String tip;
    
    private String tipPath;
    
    private String readCsvPath;
    
    public QuoteBoyType getQuoteBoyType() {
        return quoteBoyType;
    }

    public void setQuoteBoyType(QuoteBoyType quoteBoyType) {
        this.quoteBoyType = quoteBoyType;
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
    
    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
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
                .append(String.format("Quote Boy: %s\n", quoteBoyType.toString()))
                .append(String.format("Quote Throttle: %d\n", quoteThrottle))
                .append(String.format("Wire URL: %d\n", wireURL))
                .toString();
    }

    public String getReadCsvPath() {
        return readCsvPath;
    }

    public void setReadCsvPath(String readCsvPath) {
        this.readCsvPath = readCsvPath;
    }

	public String getTipPath() {
		return tipPath;
	}

	public void setTipPath(String tipPath) {
		this.tipPath = tipPath;
	}
}