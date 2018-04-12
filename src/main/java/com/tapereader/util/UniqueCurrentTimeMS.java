package com.tapereader.util;

import java.util.concurrent.atomic.AtomicLong;

public class UniqueCurrentTimeMS {
    private static final AtomicLong LAST_TIME_MS = new AtomicLong();
    
    public static long uniqueCurrentTimeMS() {
        long now = System.currentTimeMillis() * 1000;
        while(true) {
            long lastTime = LAST_TIME_MS.get();
            if (lastTime >= now)
                now = lastTime+1;
            if (LAST_TIME_MS.compareAndSet(lastTime, now))
                return now;
        }
    }
}
