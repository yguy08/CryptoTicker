package com.speculation1000.cryptoticker.core;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class GenericTickQueueImpl implements ITickQueue {

	private BlockingQueue<GenericTickImpl> queue;
	
	public GenericTickQueueImpl() {
		queue = new LinkedBlockingQueue<>(50);
	}

	@Override
	public void put(ITick tick) throws InterruptedException {
		queue.put((GenericTickImpl) tick);
	}

	@Override
	public ITick take() throws InterruptedException {
		return queue.take();
	}

}
