package com.tickercash.clerk;

public interface MarketDataClerk<T, E> {
	
	T stageMarketData(E e);

}
