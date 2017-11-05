package com.speculation1000.cryptoticker.poloniex;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.speculation1000.cryptoticker.core.ITick;
import com.speculation1000.cryptoticker.core.ITickConsumer;
import com.speculation1000.cryptoticker.core.ITickQueue;

public class PoloniexTickConsumer implements ITickConsumer {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PoloniexTickConsumer.class);
	
	private final ITickQueue queue;
	
	public PoloniexTickConsumer(ITickQueue queue) {
		this.queue = queue;
	}

	@Override
	public void consume() throws Exception {
		ITick tick = queue.take();
		LOGGER.info(tick.toString());
	}

}
