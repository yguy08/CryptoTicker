package com.tickercash.tapereader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.espertech.esper.client.EventBean;

public class TapeReaderClerk implements ITapeReader {
    
    private final Logger LOGGER = LogManager.getLogger("TapeReaderClerk");
        
    private String name;
    private ITicker ticker;
    private TipEngine tipEngine;
    private IHistoricalTickerClerk historicalTicker;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public ITicker getTicker() {
        return ticker;
    }

    @Override
    public IHistoricalTickerClerk getHistoricalTicker() {
        return historicalTicker;
    }

    @Override
    public void setTicker(ITicker ticker) {
        this.ticker = ticker;
        this.ticker.addTape(this);
    }

    @Override
    public void setHistoricalTicker(IHistoricalTickerClerk historicalTicker) {
        this.historicalTicker = historicalTicker;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void readTheTape() {
        getTicker().start();
    }

    @Override
    public TipEngine getTipEngine() {
        return tipEngine;
    }

    @Override
    public void setTipEngine(TipEngine tipEngine) {
        this.tipEngine = tipEngine;
        this.tipEngine.addListener(this);
    }

    @Override
    public void onTick(Tick tick) {
        System.out.println(tick);
    }

    @Override
    public void onEvent(Tick event, long sequence, boolean endOfBatch) throws Exception {
        getTipEngine().sendEvent(event);
    }

    @Override
    public void onStart() {
        LOGGER.info("Start Up: "+getName());
    }

    @Override
    public void onShutdown() {
        LOGGER.info("Shutting Down: "+getName());
    }

    @Override
    public void update(EventBean[] newData, EventBean[] oldData) {
          String symbol = (String) newData[0].get("symbol");
          long timestamp = (Long) newData[0].get("timestamp");
          double last = (double) newData[0].get("last");
          onTick(new Tick().set(symbol, timestamp, last));
    }
    
}
