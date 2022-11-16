package com.kekwy.se.assignment;

import java.util.UUID;

public abstract class Assignment<T> implements Runnable {

    private Thread thread;

    public void setThread(Thread thread) {
        this.thread = thread;
    }

    public Thread getThread() {
        return thread;
    }

    private boolean finished = false;
    private final Object finishedFlag = new Object();

    private T submission;

    public T getResult() {
        return submission;
    }

    protected abstract T work();

    @Override
    public void run() {
        submission = work();
        synchronized (finishedFlag) {
            finished = true;
            finishedFlag.notify();
        }
    }

    public void waitForFinished() {
        synchronized (finishedFlag) {
            if (!finished) {
                try {
                    finishedFlag.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
