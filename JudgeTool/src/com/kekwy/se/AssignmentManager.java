package com.kekwy.se;

import com.kekwy.se.assignment.Assignment;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AssignmentManager<T> {

    private final List<Assignment<T>> assignmentList = new LinkedList<>();

    public void postAssignment(List<Assignment<T>> assignments) {
        assignmentList.addAll(assignments);
    }

    public List<T> waitForData() {
        List<T> results = new LinkedList<>();
        for (Assignment<T> assignment : assignmentList) {
            try {
                assignment.getThread().join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            results.add(assignment.getResult());
        }
        return results;
    }

    public void launch() {
        ExecutorService exec = Executors.newCachedThreadPool();
        for (Assignment<T> assignment : assignmentList) {
            exec.execute(assignment);
        }
        exec.shutdown();
    }

}
