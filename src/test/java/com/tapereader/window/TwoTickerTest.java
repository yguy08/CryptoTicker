package com.tapereader.window;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.tapereader.TapeReader;
import com.tapereader.framework.Engine;
import com.tapereader.framework.Receiver;
import com.tapereader.framework.Tick;
import com.tapereader.framework.Ticker;
import com.tapereader.module.CsvTestModule;

public class TwoTickerTest extends TapeReader {
	
	@Inject
	public TwoTickerTest(Ticker ticker, Receiver receiver, Engine engine) {
		super(ticker, receiver, engine);
	}
	
	public static void main(String[] args) throws Exception{
        Injector injector = Guice.createInjector(new CsvTestModule());
        TapeReader reader = injector.getInstance(TwoTickerTest.class);
        reader.readTheTape();
	}
	
	@Override
	public void readTheTape() throws Exception{
		getEngine().addSubscriber("Select-All", this, "onTick");
		super.readTheTape();
	}
	
	public void onTick(Tick tick){
		System.out.println(tick);
	}

}
