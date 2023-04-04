package net.alis.protocoller.samples.paper;

import java.util.concurrent.atomic.AtomicLong;

public final class WeakSeqLock {
    private final AtomicLong lock = new AtomicLong();

    public void acquireWrite() {
        this.lock.lazySet(this.lock.get() + 1L);
    }

    public boolean canRead(long read) {
        return (read & 1L) == 0L;
    }

    public boolean tryAcquireWrite() {
        this.acquireWrite();
        return true;
    }

    public void releaseWrite() {
        long lock = this.lock.get();
        this.lock.lazySet(lock + 1L);
    }

    public void abortWrite() {
        long lock = this.lock.get();
        this.lock.lazySet(lock ^ 1L);
    }

    public long acquireRead() {
        int failures = 0;

        long curr;
        for(curr = this.lock.get(); !this.canRead(curr); curr = this.lock.get()) {
            ++failures;
            if (failures > 5000) {
                Thread.yield();
            }
        }

        return curr;
    }

    public boolean tryReleaseRead(long read) {
        return this.lock.get() == read;
    }

    public long getSequentialCounter() {
        return this.lock.get();
    }
}

