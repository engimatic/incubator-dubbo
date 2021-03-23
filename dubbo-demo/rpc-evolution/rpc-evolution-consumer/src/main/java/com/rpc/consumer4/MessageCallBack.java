package com.rpc.consumer4;

import com.rpc.service.ResponseEntity;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @description:
 * @author: tianjin
 * @email: eternity_bliss@sina.cn
 * @create: 2021-03-22 下午5:58
 */
public class MessageCallBack {
    private String id;
    private ResponseEntity response;

    private Lock lock = new ReentrantLock();
    private Condition finish = lock.newCondition();

    public MessageCallBack(String id) {
        this.id = id;
    }

    public Object get() throws InterruptedException {
        try {
            lock.lock();
            finish.await(1000, TimeUnit.MILLISECONDS);
            if (response != null) {
                return response.getRes();
            } else {
                return null;
            }
        } finally {
            lock.unlock();
        }
    }

    public void over(ResponseEntity reponse) {
        try {
            lock.lock();
            finish.signal();
            this.response = reponse;
        } finally {
            lock.unlock();
        }
    }
}
