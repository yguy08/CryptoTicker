package com.speculation1000.cryptoticker.core;

public interface ITickConsumer {
	void consume() throws InterruptedException, Exception;
}
