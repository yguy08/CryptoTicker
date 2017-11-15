package com.speculation1000.cryptoticker.core;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.speculation1000.cryptoticker.model.TickVO;

public class TickQueue {

	private BlockingQueue<TickVO> queue;
	
	public TickQueue() {
		queue = new LinkedBlockingQueue<>(50);
	}

	public void put(TickVO tick) throws InterruptedException {
		queue.put(tick);
	}

	public TickVO take() throws InterruptedException {
		return queue.take();
	}

}
