package com.speculation1000.cryptoticker.core;

import static org.junit.Assert.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.junit.Test;

public class UniqueCurrentTimeMSTest {
	@Test
	public void uniqueCurrentTimeMSTest() {
		final int loop = 500_000;
		Map<Long,Integer> keys = new ConcurrentHashMap<>();
		for(int i = 0;i<loop;i++) {
			keys.put(UniqueCurrentTimeMS.uniqueCurrentTimeMS(),i);
		}
		assertTrue(keys.size()==loop);
	}
}
