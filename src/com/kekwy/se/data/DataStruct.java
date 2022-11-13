package com.kekwy.se.data;

import java.util.LinkedList;
import java.util.List;

@SuppressWarnings("rawtypes")
public class DataStruct {

    private final List<IOPort> nextSteps = new LinkedList<>();

    public IOPort getNextStep() {
        return nextSteps.remove(0);
    }
}
