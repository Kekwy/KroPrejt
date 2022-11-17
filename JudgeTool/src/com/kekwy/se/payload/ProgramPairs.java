package com.kekwy.se.payload;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 等价与不等价的程序对组，等价性判断任务的输出结果
 */
public class ProgramPairs {

    public String getEqualPairsString(String prefix) {
        return pairsToString(equalPairs, prefix);
    }

    private String pairsToString(List<ProgramPair> pairList, String prefix) {
        StringBuilder res = new StringBuilder();
        for (ProgramPair pair : pairList) {
            String path1 = pair.file1.getParent().substring(
                    pair.file1.getParent().lastIndexOf("/") + 1) +
                    "/" + pair.file1.getName();
            String path2 = pair.file2.getParent().substring(
                    pair.file2.getParent().lastIndexOf("/") + 1) +
                    "/" + pair.file2.getName();
            res.append(prefix).
                    append(path1).
                    append(",").
                    append(prefix).
                    append(path2).
                    append("\n");
        }
        return res.toString();
    }

    public String getInequalPairsString(String prefix) {
        return pairsToString(inequalPairs, prefix);
    }

    private final List<ProgramPair> equalPairs = new ArrayList<>();
    private final List<ProgramPair> inequalPairs = new ArrayList<>();

    public void addEqualPair(File file1, File file2) {
        equalPairs.add(new ProgramPair(file1, file2));
    }

    public void addInequalPair(File file1, File file2) {
        inequalPairs.add(new ProgramPair(file1, file2));
    }

    private static class ProgramPair {
        public File file1;
        public File file2;

        public ProgramPair(File file1, File file2) {
            this.file1 = file1;
            this.file2 = file2;
        }
    }
}
