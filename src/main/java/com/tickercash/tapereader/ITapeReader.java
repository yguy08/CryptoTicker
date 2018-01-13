package com.tickercash.tapereader;

import com.tickercash.model.Tick;

/**
 * Tape Reader Clerk sets the context
 * @author Wyatt 
 * 
 */
public interface ITapeReader extends ITip {
    
    /**
     * Get TipEngine
     */
    public TipEngine getTipEngine();
    
    /**
     * Get Ticker
     */
    public ITicker getTicker();
    
    /**
     * Get Historical Ticker
     */
    public IHistoricalTickerClerk getHistoricalTicker();

    /**
     * Get Tape Reader Name
     */
    public String getName();
    
    /**
     * Set Ticker
     */
    public void setTicker(ITicker ticker);
    
    /**
     * Set Historical Ticker
     */
    public void setHistoricalTicker(IHistoricalTickerClerk historicalTicker);
    
    /**
     * Set Tape Reader Name
     */
    public void setName(String name);
    
    /**
     * Set Tip Engine
     */
    public void setTipEngine(TipEngine tipEngine);
    
    /**
     * On Read the Tape
     */
    public void readTheTape();
    
    /**
     * On New Tick
     */
    public void onTick(Tick tick);
    
}
