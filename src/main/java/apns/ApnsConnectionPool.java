package apns;

import java.util.HashSet;
import java.util.Set;

public class ApnsConnectionPool {

    private int mCapacity;
    private Set<ApnsConnection> mPool = new HashSet<>();

    public ApnsConnectionPool(int capacity) {
        mCapacity = capacity;
    }

    public synchronized ApnsConnection obtain() {
        if (mPool.size() > 0) {
            ApnsConnection next = mPool.iterator().next();
            mPool.remove(next);
            return next;
        }
        return null;
    }

    public synchronized void put(ApnsConnection connection) {
        if (connection == null) {
            throw new IllegalArgumentException("connection must not be null");
        }
        if (mPool.size() < mCapacity) {
            mPool.add(connection);
        }
    }

    public synchronized int getSize() {
        return mPool.size();
    }

    public int getCapacity() {
        return mCapacity;
    }
}
