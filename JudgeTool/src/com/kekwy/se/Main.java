package com.kekwy.se;

import com.kekwy.se.payload.ProgramPairs;
import com.kekwy.se.payload.SourceCodeGroup;

import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        if(args.length == 0) {
            throw new RuntimeException("未提供文件路径");
        }
        // 通过程序实参获取需要对比的文件所在的目录，并生成文件对象
        File directory = new File(args[0]);
        // 向IO控制器传递要处理的文件目录
        IOController ioController = new IOController(directory);
        // 通过IO控制器根据目录下相应文件的信息构造数据结构
        List<SourceCodeGroup> sourceCodeGroupList = ioController.loadSourceCodeGroups();
        // 实例化判断工具控制器
        JudgeToolController judgeToolController = new JudgeToolController();
        // 令判断工具控制器根据之前构造的数据结构，创建判断任务
        judgeToolController.createAssignments(sourceCodeGroupList);
        // 执行判断任务，并等待结果。返回值为每个字目录下程序的等价对与不等价对
        List<ProgramPairs> programPairsList = judgeToolController.runAssignments();
        // 令IO控制器根据返回结果生成对应的CSV文件
        ioController.toCSVFiles(programPairsList);
        // 提示用户，判断结束，成功生成目标文件
        System.out.println("对比结束，CSV文件生成于: " + new File("./output").getAbsolutePath());

    }
}



class test {

}