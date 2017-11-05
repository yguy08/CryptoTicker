package com.speculation1000.cryptoticker.core;

public interface ITickQueue {
	void put(ITick tick) throws InterruptedException;
	
	ITick take() throws InterruptedException;
}
