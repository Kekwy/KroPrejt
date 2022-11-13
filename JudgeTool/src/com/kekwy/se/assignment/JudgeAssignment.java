package com.kekwy.se.assignment;

import com.kekwy.se.data.DataStruct;

import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class JudgeAssignment implements Runnable {
    // private final DataStruct dataStruct;
    private final int threadNum;

    private final Thread[] workThreads;

    public JudgeAssignment(List<URL> execFiles, int threadNum) {
        // this.dataStruct = dataStruct;
        this.threadNum = threadNum;
        this.workThreads = new Thread[threadNum];
    }

    private static final ExecutorService exec = Executors.newCachedThreadPool();

    private class workUnit implements Runnable {

        //public workUnit(List<URL>)

        @Override
        public void run() {

        }
    }

    @Override
    public void run() {
        prepare();
        for (int i = 0; i < threadNum; i++) {
            synchronized (exec) {
                final int finalI = i;
                //workThreads[i] = new Thread(((int a)->()->work(a))(i));
                exec.execute(workThreads[i]);
            }
        }
        for (int i = 0; i < threadNum; i++) {
            try {
                workThreads[i].join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        // ioPort.send(dataStruct, dataStruct.getNextStep());
    }


    private void prepare() {

    }



}
