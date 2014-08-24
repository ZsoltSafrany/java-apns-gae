package apns;

import java.util.HashSet;
import java.util.Set;

/**
 * A simple and thread-safe connection pool for ApnsConnection instances.
 */
public class ApnsConnectionPool {

    private int mCapacity;
    private Set<ApnsConnection> mPool = new HashSet<>();

    /**
     * @param capacity Maximum size of the pool; maximum number of ApnsConnection instances in the pool.
     */
    public ApnsConnectionPool(int capacity) {
        mCapacity = capacity;
    }

    /**
     * If, and only if, there is at least one connection in the pool then one of the connections will be returned
     * and size of the pool will be decremented by one (i.e. returned connection is removed from the pool).
     *
     * @return one of the connections in the pool if, and only if, there is at least one connection in the pool;
     * otherwise null
     */
    public synchronized ApnsConnection obtain() {
        if (mPool.size() > 0) {
            ApnsConnection next = mPool.iterator().next();
            mPool.remove(next);
            return next;
        }
        return null;
    }

    /**
     * If and only if size of the pool is less than its capacity then
     * connection is put into the pool and size of the pool incremented by one;
     * otherwise method argument is ignored and pool is not changed.
     *
     * @param connection that you want to put into the pool
     */
    public synchronized void put(ApnsConnection connection) {
        if (connection == null) {
            throw new IllegalArgumentException("connection must not be null");
        }
        if (mPool.size() < mCapacity) {
            mPool.add(connection);
        }
    }

    /**
     * @return number of connections in the pool
     */
    public synchronized int getSize() {
        return mPool.size();
    }

    /**
     * @return maximum number of connections in the pool (defined at construction time)
     */
    public int getCapacity() {
        return mCapacity;
    }
}
