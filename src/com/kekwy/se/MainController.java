package com.kekwy.se;

import com.kekwy.se.data.DataStruct;
import com.kekwy.se.data.IOPort;

public class MainController {

    private static MainController mainController;

    private MainController() {
    }

    public static MainController getMainController() {
        if (mainController == null) {
            mainController = new MainController();
        }
        return mainController;
    }

    public IOPort<DataStruct> ioPort;

    private boolean active = true;





    private void send() {
        // while (active) {
            // DataStruct data = assignmentManager.waitForData();
            // ioPort.send(data, null);
        // }
    }
    private void receive() {
        while (active) {
            DataStruct data = ioPort.get();
            if(data == null) {
                active = false;
                // assignmentManager.setActive(false);
            } else {
                // createAssignment(data);
            }
        }
        // TODO 保存数据退出运行
    }

    public void start() {
        new Thread(this::send).start();
        new Thread(this::receive).start();
    }
}
