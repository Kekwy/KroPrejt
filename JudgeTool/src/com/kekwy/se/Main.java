package com.kekwy.se;

import com.kekwy.se.payload.ProgramPairs;
import com.kekwy.se.payload.SourceCodeGroup;

import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) {

        /*if(args.length == 0) {
            throw new RuntimeException("未提供文件路径");
        }*/

        // File directory = new File(args[0]);
        File directory = new File("/Users/kekwy/Downloads/input");

        IOController ioController = new IOController(directory);

        List<SourceCodeGroup> sourceCodeGroupList = ioController.loadSourceCodeGroups();

        JudgeToolController judgeToolController = new JudgeToolController();

        judgeToolController.createAssignments(sourceCodeGroupList);

        List<ProgramPairs> programPairsList = judgeToolController.runAssignments();

        ioController.toCSVFiles(programPairsList);

        System.out.println("对比结束，CSV文件生成于: " + directory.getAbsolutePath());

    }
}