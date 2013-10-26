package dao;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public abstract class Locker {
    final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    final Lock readLock = lock.readLock();
    final Lock writeLock = lock.writeLock();
}
