package com.kekwy.se.data;

import java.util.UUID;

public class DataStruct {

    private final UUID REQUEST_UUID;
    // private final List<IOPort> nextSteps = new LinkedList<>();

    public UUID getUUID() {
        return REQUEST_UUID;
    }
    public DataStruct(UUID uuid, Loadable payLoad) {
        this.REQUEST_UUID = uuid;
        this.payLoad = payLoad;
    }

    private final Loadable payLoad;

    /*public IOPort getNextStep() {
        return nextSteps.remove(0);
    }*/

    public Loadable getPayLoad() {
        return payLoad;
    }

}
