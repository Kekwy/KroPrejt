package com.kekwy.se.data;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ProgramPairs {


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
