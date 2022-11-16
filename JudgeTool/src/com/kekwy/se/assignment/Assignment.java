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

    public boolean isFinished() {
        synchronized (finishedFlag) {
            return finished;
        }
    }

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
        }
    }
}
