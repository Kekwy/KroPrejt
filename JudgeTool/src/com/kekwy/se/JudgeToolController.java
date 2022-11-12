package com.kekwy.se;

import java.util.HashMap;
import java.util.Map;

public class JudgeToolController implements Runnable{
    private static JudgeToolController judgeToolController;

    private JudgeToolController() {}

    public static JudgeToolController getJudgeToolController() {
        if(judgeToolController == null) {
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


    @Override
    public void run() {

    }
}
