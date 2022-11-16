package com.kekwy.se;

import com.kekwy.se.payload.ProgramPairs;
import com.kekwy.se.payload.SourceCodeGroup;

import java.io.File;
import java.util.List;

public class IOController {

    private final File directory;

    public IOController(File directory) {
        this.directory = new File(directory);
        if(!this.directory.exists()) {
            throw new RuntimeException("目标路径不存在！");
        }
    }

    public List<SourceCodeGroup> loadSourceCodeGroups() {
        return null;
    }

    public File[] toCSVFiles(List<ProgramPairs> programPairsList) {
        return null;
    }
}
