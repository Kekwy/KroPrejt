package com.kekwy.se;

import com.kekwy.se.payload.ProgramPairs;
import com.kekwy.se.payload.SourceCodeGroup;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class IOController {

    private final File directory;

    public IOController(File directory) {
        this.directory = directory;
        if (!this.directory.exists() && !this.directory.isDirectory()) {
            throw new RuntimeException("目标路径不存在");
        }
    }

    public List<SourceCodeGroup> loadSourceCodeGroups() {
        List<SourceCodeGroup> sourceCodeGroupList = new ArrayList<>();
        File[] subDirectories = directory.listFiles();
        if (subDirectories == null) {
            throw new RuntimeException("所选目录为空");
        }
        for (File subDirectory : subDirectories) {
            File[] files = subDirectory.listFiles();
            if (files == null) {
                continue;
            }
            List<File> sourceCodeList = new ArrayList<>();
            File stdInFormat = null;
            String language = null;
            for (File file : files) {
                if (file.isHidden()) {
                    continue;
                }
                if (file.getName().equals("stdin_format.txt")) {
                    stdInFormat = file;
                } else {
                    if (language == null) {
                        String name = file.getName();
                        switch (name.substring(name.lastIndexOf('.') + 1)) {
                            case "c" -> language = "c";
                            case "cpp" -> language = "cpp";
                            case "py" -> language = "python";
                            case "java" -> language = "java";
                            default -> throw new RuntimeException("尚未支持的语言");
                        }
                    }
                    sourceCodeList.add(file);
                }
            }
            if (stdInFormat == null) {
                throw new RuntimeException("未发现标准输入格式文件");
            }
            sourceCodeGroupList.add(new SourceCodeGroup(sourceCodeList, language, stdInFormat));
        }
        return sourceCodeGroupList;
    }

    public void toCSVFiles(List<ProgramPairs> programPairsList) {
        File dir = new File("./output/");
        if (!dir.exists()) {
            if (!dir.mkdirs()) {
                throw new RuntimeException("");
            }
        }
        File equalPair = new File("./output/" + "equal.csv");
        File inequalPair = new File("./output/" + "inequal.csv");
        try {
            if (!equalPair.exists() && !inequalPair.exists()) {
                if (!equalPair.createNewFile() || !inequalPair.createNewFile()) {
                    throw new RuntimeException("文件创建失败");
                }
            }
            BufferedWriter bfWt1 = new BufferedWriter(new FileWriter(equalPair));
            BufferedWriter bfWt2 = new BufferedWriter(new FileWriter(inequalPair));
            bfWt1.write("file1,file2\n");
            bfWt2.write("file1,file2\n");
            for (ProgramPairs programPairs : programPairsList) {
                bfWt1.write(programPairs.getEqualPairsString(directory.getName() + "/"));
                bfWt2.write(programPairs.getInequalPairsString(directory.getName() + "/"));
            }
            bfWt1.close();
            bfWt2.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
