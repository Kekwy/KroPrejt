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
        while (!assignmentList.isEmpty()) {
            ListIterator<Assignment<T>> listIterator = assignmentList.listIterator();
            while (listIterator.hasNext()) {
                Assignment<T> assignment = listIterator.next();
                assignment.waitForFinished();
                results.add(assignment.getResult());
                listIterator.remove();
            }
        }
        return results;
    }

    public void launch() {
        ExecutorService exec = Executors.newCachedThreadPool();
        for (Assignment<T> assignment : assignmentList) {
            assignment.setThread(new Thread(assignment));
            exec.execute(assignment.getThread());
        }
        exec.shutdown();
    }

}
