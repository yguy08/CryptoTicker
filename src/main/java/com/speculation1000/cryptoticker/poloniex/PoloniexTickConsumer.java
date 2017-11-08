package com.speculation1000.cryptoticker.poloniex;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.speculation1000.cryptoticker.core.ITickConsumer;
import com.speculation1000.cryptoticker.core.TickQueue;
import com.speculation1000.cryptoticker.marketdataevent.Tick;

public class PoloniexTickConsumer implements ITickConsumer {
	
    private static final Logger logger = LogManager.getLogger("PoloniexTickConsumer");
	
	private final TickQueue queue;
	
	public PoloniexTickConsumer(TickQueue queue) {
		this.queue = queue;
	}

	@Override
	public void consume() throws Exception {
		Tick tick = queue.take();
		logger.info(tick.toString());
	}

}
