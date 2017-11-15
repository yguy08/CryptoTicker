package com.speculation1000.cryptoticker.core;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.speculation1000.cryptoticker.model.Tick;

public class TickQueue {

	private BlockingQueue<Tick> queue;
	
	public TickQueue() {
		queue = new LinkedBlockingQueue<>(50);
	}

	public void put(Tick tick) throws InterruptedException {
		queue.put(tick);
	}

	public Tick take() throws InterruptedException {
		return queue.take();
	}

}
