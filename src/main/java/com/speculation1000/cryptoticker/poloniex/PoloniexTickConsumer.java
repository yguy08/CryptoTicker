package com.speculation1000.cryptoticker.poloniex;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.speculation1000.cryptoticker.core.ITick;
import com.speculation1000.cryptoticker.core.ITickConsumer;
import com.speculation1000.cryptoticker.core.ITickQueue;

public class PoloniexTickConsumer implements ITickConsumer {
	
    private static final Logger logger = LogManager.getLogger("PoloniexTickConsumer");
	
	private final ITickQueue queue;
	
	public PoloniexTickConsumer(ITickQueue queue) {
		this.queue = queue;
	}

	@Override
	public void consume() throws Exception {
		ITick tick = queue.take();
		logger.info(tick.toString());
	}

}
