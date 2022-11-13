package com.kekwy.se;

import com.kekwy.se.assignment.Generator;
import com.kekwy.se.assignment.Preprocessor;
import com.kekwy.se.data.DataStruct;
import com.kekwy.se.data.IOPort;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

    private Generator generator;

    private final Map<String, Preprocessor> preprocessorMap = new HashMap<>();

    private void setGenerator(Generator generator) {
        this.generator = generator;
    }

    private void addPreprocessor(String programLanguage, Preprocessor preprocessor) {
        preprocessorMap.put(programLanguage, preprocessor);
    }

    private boolean active = true;

    private final List<String> receiveBuffer = new LinkedList<>();

    private final ExecutorService exec = Executors.newCachedThreadPool();

    private void send() {

    }

    private void createAssignment(DataStruct data) {

    }

    public final IOPort<DataStruct> ioPort = new IOPort<>();

    private void receive() {
        while (active) {
            DataStruct data = ioPort.get();
            if(data == null) {
                active = false;
            } else {

            }
        }
    }

    public void start() {
        new Thread(this::send).start();
        new Thread(this::receive).start();
    }



}
