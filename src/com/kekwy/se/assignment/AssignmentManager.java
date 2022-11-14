package com.kekwy.se.assignment;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AssignmentManager<T> implements Runnable {

    private final List<Assignment<T>> assignmentList = new LinkedList<>();
    private final List<T> submissions = new LinkedList<>();

    private ListIterator<Assignment<T>> iterator;

    private final ExecutorService exec = Executors.newCachedThreadPool();

    public void postAssignment(Assignment<T> assignment) {
        synchronized (assignmentList) {
            iterator.add(assignment);
            assignmentList.notify();
        }
        assignment.setThread(new Thread(assignment));
        synchronized (exec) {
            exec.execute(assignment.getThread());
        }
    }

    private void shutdown() {
        exec.shutdown();
    }

    public T waitForData() {
        T data;
        synchronized (submissions) {
            if (submissions.isEmpty()) {
                try {
                    submissions.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            data = submissions.remove(0);
        }
        return data;
    }

    private boolean active = true;

    public void setActive(boolean b) {
        active = b;
    }

    @Override
    public void run() {
        while (active) {
            synchronized (assignmentList) {
                iterator = assignmentList.listIterator();
            }
            while (true) {
                Assignment<T> currentAssignment;
                synchronized (assignmentList) {
                    if (!iterator.hasNext()) {
                        break;
                    } else {
                        currentAssignment = iterator.next();
                    }
                }
                if(currentAssignment.isFinished()) {
                    synchronized (assignmentList) {
                        iterator.remove();
                    }
                    synchronized (submissions) {
                        submissions.add(currentAssignment.getSubmission());
                        submissions.notify();
                    }
                }
            }
            synchronized (assignmentList) {
                if (assignmentList.isEmpty()) {
                    try {
                        assignmentList.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        shutdown();
    }
}
