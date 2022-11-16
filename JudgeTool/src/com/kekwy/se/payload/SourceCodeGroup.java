package com.kekwy.se.payload;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 待测程序的组，作为判断工具的输入
 */
public class SourceCodeGroup {
    private final List<File> fileList;                                // 源代码文件
    private final String language;                                    // 源代码语言
    private final List<InputInfo> inputInfoList = new ArrayList<>();  // 输入信息列表

    private final static Pattern INPUT_INFO_PATTERN =                 // 用户提供输入信息的格式
            Pattern.compile("(\\w+)[(](\\d+),(\\d+)[)]");

    /**
     * 从文件中读取输入信息
     * @param fileList 待测程序源码的列表
     * @param language 待测程序源码的语言
     * @param inputInfoFile 保存待测程序输入信息的文件
     */
    public SourceCodeGroup(List<File> fileList, String language, File inputInfoFile) {
        this.fileList = fileList;
        this.language = language;
        String inputInfo;
        BufferedReader bfIs;
        try {
            bfIs = new BufferedReader(new FileReader(inputInfoFile));
            inputInfo = bfIs.readLine();
            bfIs.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        readInputInfo(inputInfo);
    }

    /**
     * 使用正则表达式解析输入信息
     */
    void readInputInfo(String inputInfo) {
        String[] inputInfoList = inputInfo.split(" ");
        for (String s : inputInfoList) {
            Matcher matcher = INPUT_INFO_PATTERN.matcher(s);
            if(matcher.find()) {
                String typeString = matcher.group(1);
                int begin = Integer.parseInt(matcher.group(2));
                int end = Integer.parseInt(matcher.group(3));
                this.inputInfoList.add(new InputInfo(typeString, begin, end));
                /*System.out.println(matcher.group(1));
                System.out.println(matcher.group(2));
                System.out.println(matcher.group(3));*/
            } else {
                // TODO 提示用户格式有误
            }
        }
    }

    public String getLanguage() {
        return language;
    }

    public List<InputInfo> getInputType() {
        return inputInfoList;
    }

    public List<File> getFileList() {
        return fileList;
    }
}
