package dao;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class DataStore extends Locker {

    protected ConcurrentHashMap<Integer, Object> store = null;

    public DataStore() {
        store = new ConcurrentHashMap<Integer, Object>();
    }

    public void clear() {
        writeLock.lock();
        try {
            store.clear();
        } finally {
            writeLock.unlock();
        }
    }

    public Object[] getValues() {
        Object[] objects = null;
        readLock.lock();
        try {
            objects = store.values().toArray();
        } finally {
            readLock.unlock();
        }
        return objects;
    }

    public int count() {
        int size = 0;
        readLock.lock();
        try {
            size = store.size();
        } finally {
            readLock.unlock();
        }
        return size;
    }

    public Object get(int id) {
        Object o = null;
        readLock.lock();
        try {
            o = store.get(id);
        } finally {
            readLock.unlock();
        }
        return o;
    }

    public void update(int id, Object o) {
        add(id, o);
    }

    public void addAll(Map<Integer, Object> values) {
        writeLock.lock();
        try {
            store.putAll(values);
        } finally {
            writeLock.unlock();
        }
    }

    public void add(int id, Object o) {
        writeLock.lock();
        try {
            store.putIfAbsent(id, o);
        } finally {
            writeLock.unlock();
        }
    }

    public void remove(int id) {
        writeLock.lock();
        try {
            store.remove(id);
        } finally {
            writeLock.unlock();
        }
    }
}
