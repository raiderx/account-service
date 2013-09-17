package org.accountservice.server.model;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Pavel Karpukhin
 */
public class Statistics {

    private AtomicLong start = new AtomicLong();
    private AtomicLong count = new AtomicLong(0);

    public Statistics() {
        this.start = new AtomicLong(System.currentTimeMillis());
        this.count = new AtomicLong(0);
    }

    public synchronized void reset() {
        start.set(System.currentTimeMillis());
        count.set(0);
    }

    public void increase() {
        count.incrementAndGet();
    }

    public long getCountPerSecond() {
        long start = this.start.get();
        long count = this.count.get();
        long end = System.currentTimeMillis();
        if (end - start < 1000) {
            return 0;
        }
        return 1000 * count / (end - start);
    }

    public long getCountPerMinute() {
        long start = this.start.get();
        long count = this.count.get();
        long end = System.currentTimeMillis();
        if (end - start < 60 * 1000) {
            return 0;
        }
        return 60 * 1000 * count / (end - start);
    }
}
