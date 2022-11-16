package com.kekwy.se;

import com.kekwy.se.assignment.AssignmentManager;
import com.kekwy.se.data.DataStruct;
import com.kekwy.se.data.IOPort;
import com.kekwy.se.payload.ProgramPairs;
import com.kekwy.se.payload.SourceCodeGroup;
import com.sun.tools.javac.Main;

public class JudgeToolController {
    private static JudgeToolController judgeToolController;

    private JudgeToolController() {
    }

    public static JudgeToolController getJudgeToolController() {
        if (judgeToolController == null) {
            judgeToolController = new JudgeToolController();
        }
        return judgeToolController;
    }

    private boolean active = true;

    private void send() {
        while (active) {
            DataStruct data = assignmentManager.waitForData();
            ioPort.send(data, MainController.getMainController().ioPort);
        }
    }

    private void createAssignment(DataStruct data) {
        if (data.getPayLoad() instanceof SourceCodeGroup group) {
            JudgeAssignment assignment = new JudgeAssignment(group.getFileList(),
                    group.getLanguage(), group.getInputType());
            assignmentManager.postAssignment(assignment);
        } else {
            // TODO 进行错误处理
        }
    }
    public final IOPort<DataStruct> ioPort = new IOPort<>();

    private final AssignmentManager<ProgramPairs> assignmentManager = new AssignmentManager<>();

    private void receive() {
        while (active) {
            DataStruct data = ioPort.get();
            if(data == null) {
                active = false;
                assignmentManager.setActive(false);
            } else {
                createAssignment(data);
            }
        }
        // TODO 保存数据退出运行
    }

    public void start() {
        new Thread(this::send).start();
        new Thread(this::receive).start();
        new Thread(assignmentManager).start();
    }

}
