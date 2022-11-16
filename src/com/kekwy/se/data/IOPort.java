package com.kekwy.se.data;

import java.util.LinkedList;
import java.util.List;

public class IOPort<T> {
    private final List<T> receiveBuffer = new LinkedList<>();

    public T get() {
        T data;
        synchronized (receiveBuffer) {
            if (receiveBuffer.isEmpty()) {
                try {
                    receiveBuffer.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            data = receiveBuffer.remove(0);
        }
        return data;
    }

    public void send(T dataStruct, IOPort<T> nextStep) {
        nextStep.post(dataStruct);
    }

    public void post(T data) {
        synchronized (receiveBuffer) {
            receiveBuffer.add(data);
            receiveBuffer.notify();
        }
    }

}
