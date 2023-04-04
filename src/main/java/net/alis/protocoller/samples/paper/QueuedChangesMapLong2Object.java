package net.alis.protocoller.samples.paper;

import it.unimi.dsi.fastutil.longs.Long2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class QueuedChangesMapLong2Object<V> {
    protected static final Object REMOVED = new Object();
    protected final Long2ObjectLinkedOpenHashMap<V> updatingMap;
    protected final Long2ObjectLinkedOpenHashMap<V> visibleMap;
    protected final Long2ObjectLinkedOpenHashMap<Object> queuedChanges;
    protected final WeakSeqLock updatingMapSeqLock;

    public QueuedChangesMapLong2Object() {
        this(16, 0.75F);
    }

    public QueuedChangesMapLong2Object(int capacity, float loadFactor) {
        this.updatingMapSeqLock = new WeakSeqLock();
        this.updatingMap = new Long2ObjectLinkedOpenHashMap(capacity, loadFactor);
        this.visibleMap = new Long2ObjectLinkedOpenHashMap(capacity, loadFactor);
        this.queuedChanges = new Long2ObjectLinkedOpenHashMap();
    }

    public V queueUpdate(long k, V value) {
        this.queuedChanges.put(k, value);
        return this.updatingMap.put(k, value);
    }

    public V queueRemove(long k) {
        this.queuedChanges.put(k, REMOVED);
        return this.updatingMap.remove(k);
    }

    public V getUpdating(long k) {
        return this.updatingMap.get(k);
    }

    public boolean updatingContainsKey(long k) {
        return this.updatingMap.containsKey(k);
    }

    public V getVisible(long k) {
        return this.visibleMap.get(k);
    }

    public boolean visibleContainsKey(long k) {
        return this.visibleMap.containsKey(k);
    }

    public V getVisibleAsync(long k) {
        V ret = null;

        long readlock;
        do {
            readlock = this.updatingMapSeqLock.acquireRead();

            try {
                ret = this.visibleMap.get(k);
            } catch (Throwable throwable) {
                if (throwable instanceof ThreadDeath) {
                    throw (ThreadDeath)throwable;
                }
            }
        } while(!this.updatingMapSeqLock.tryReleaseRead(readlock));

        return ret;
    }

    public boolean visibleContainsKeyAsync(long k) {
        boolean ret = false;

        long readlock;
        do {
            readlock = this.updatingMapSeqLock.acquireRead();

            try {
                ret = this.visibleMap.containsKey(k);
            } catch (Throwable throwable) {
                if (throwable instanceof ThreadDeath) {
                    throw (ThreadDeath)throwable;
                }
            }
        } while(!this.updatingMapSeqLock.tryReleaseRead(readlock));

        return ret;
    }

    public Long2ObjectLinkedOpenHashMap<V> getVisibleMap() {
        return this.visibleMap;
    }

    public Long2ObjectLinkedOpenHashMap<V> getUpdatingMap() {
        return this.updatingMap;
    }

    public int getVisibleSize() {
        return this.visibleMap.size();
    }

    public int getVisibleSizeAsync() {
        long readlock;
        int ret;
        do {
            readlock = this.updatingMapSeqLock.acquireRead();
            ret = this.visibleMap.size();
        } while(!this.updatingMapSeqLock.tryReleaseRead(readlock));

        return ret;
    }

    public Collection<V> getUpdatingValues() {
        return this.updatingMap.values();
    }

    public List<V> getUpdatingValuesCopy() {
        return new ArrayList<>(this.updatingMap.values());
    }

    public Collection<V> getVisibleValues() {
        return this.visibleMap.values();
    }

    public List<V> getVisibleValuesCopy() {
        return new ArrayList<>(this.visibleMap.values());
    }

    public boolean performUpdates() {
        if (this.queuedChanges.isEmpty()) {
            return false;
        } else {
            ObjectBidirectionalIterator<Long2ObjectMap.Entry<Object>> iterator = this.queuedChanges.long2ObjectEntrySet().fastIterator();

            while(iterator.hasNext()) {
                Long2ObjectMap.Entry<Object> entry = iterator.next();
                long key = entry.getLongKey();
                Object val = entry.getValue();
                this.updatingMapSeqLock.acquireWrite();

                try {
                    if (val == REMOVED) {
                        this.visibleMap.remove(key);
                    } else {
                        this.visibleMap.put(key, (V) val);
                    }
                } finally {
                    this.updatingMapSeqLock.releaseWrite();
                }
            }

            this.queuedChanges.clear();
            return true;
        }
    }

    public boolean performUpdatesLockMap() {
        if (this.queuedChanges.isEmpty()) {
            return false;
        } else {
            ObjectBidirectionalIterator<Long2ObjectMap.Entry<Object>> iterator = this.queuedChanges.long2ObjectEntrySet().fastIterator();

            try {
                this.updatingMapSeqLock.acquireWrite();

                while(iterator.hasNext()) {
                    Long2ObjectMap.Entry<Object> entry = iterator.next();
                    long key = entry.getLongKey();
                    Object val = entry.getValue();
                    if (val == REMOVED) {
                        this.visibleMap.remove(key);
                    } else {
                        this.visibleMap.put(key, (V) val);
                    }
                }
            } finally {
                this.updatingMapSeqLock.releaseWrite();
            }

            this.queuedChanges.clear();
            return true;
        }
    }
}

