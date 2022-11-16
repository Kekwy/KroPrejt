package com.kekwy.se;

import com.kekwy.se.assignment.AssignmentManager;
import com.kekwy.se.data.DataStruct;
import com.kekwy.se.data.IOPort;
import com.kekwy.se.data.ProgramPairs;
import com.kekwy.se.data.SourceCodeGroup;

import java.io.File;
import java.util.*;

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
            // uuidDataStructMap.put(assignment.getUUID(), data);
            // DataStruct dataStruct = new DataStruct();
            ioPort.send(data, null);
        }
    }


    private final Map<UUID, DataStruct> uuidDataStructMap = new HashMap<>();

    private void createAssignment(DataStruct data) {
        if (data.getPayLoad() instanceof SourceCodeGroup group) {
            JudgeAssignment assignment = new JudgeAssignment(group.getFileList(),
                    group.getLanguage(), group.getInputType());
            uuidDataStructMap.put(assignment.getUUID(), data);
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
    }



}
