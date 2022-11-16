package com.kekwy.se.assignment;

import com.kekwy.se.data.DataStruct;
import com.kekwy.se.data.Loadable;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AssignmentManager<T extends Loadable> implements Runnable {

    private final List<Assignment<T>> assignmentList = new LinkedList<>();
    private final List<DataStruct> submissions = new LinkedList<>();

    private ListIterator<Assignment<T>> iterator;

    private final ExecutorService exec = Executors.newCachedThreadPool();

    public AssignmentManager() {
        iterator = assignmentList.listIterator();
    }

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

    public DataStruct waitForData() {
        synchronized (submissions) {
            if (submissions.isEmpty()) {
                try {
                    submissions.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            return submissions.remove(0);
        }
        // return new DataStruct(submission.uuid, submission.data);
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
                        submissions.add(new DataStruct(currentAssignment.getUUID(),
                                currentAssignment.getResult()));
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
