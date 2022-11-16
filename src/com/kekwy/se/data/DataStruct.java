package com.kekwy.se.data;

import java.util.UUID;

public class DataStruct {

    private final UUID REQUEST_UUID = UUID.randomUUID();
    // private final List<IOPort> nextSteps = new LinkedList<>();

    public UUID getUUID() {
        return REQUEST_UUID;
    }

    private Loadable payLoad;

    /*public IOPort getNextStep() {
        return nextSteps.remove(0);
    }*/

    public Loadable getPayLoad() {
        return payLoad;
    }

}
