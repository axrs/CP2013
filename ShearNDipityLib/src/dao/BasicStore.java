package dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class BasicStore extends Locker {

    List store = null;

    public BasicStore() {
        store = Collections.synchronizedList(new ArrayList());
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
            objects = store.toArray();
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

    public void update(Object o) {
        remove(o);
        add(o);
    }

    public void addAll(List<Object> values) {
        writeLock.lock();
        try {
            store.addAll(values);
        } finally {
            writeLock.unlock();
        }
    }

    public void add(Object o) {
        writeLock.lock();
        try {
            store.add(o);
        } finally {
            writeLock.unlock();
        }
    }

    public void remove(Object o) {
        writeLock.lock();
        try {
            store.remove(o);
        } finally {
            writeLock.unlock();
        }
    }
}
