package com.kekwy.se;

import com.kekwy.se.assignment.Compiler;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class CppCompiler implements Compiler {
    private final ProcessBuilder builder = new ProcessBuilder();

    public CppCompiler() {
        File outputPath = new File("./tmp/exec/");
        if(!(outputPath.exists() && outputPath.isDirectory())) {
            if(!outputPath.mkdirs()) {
                throw new RuntimeException("编译输出路径不存在且创建失败");
            }
        }
    }

    @Override
    public List<File> compile(List<File> sourceCode) throws IOException {
        List<File> execFiles = new ArrayList<>();
        for (File file : sourceCode) {
            String sourceName = file.getName();
            String prefix = UUID.randomUUID().toString().trim().replaceAll("-", "");
            String outFileName = prefix + sourceName.substring(0, sourceName.indexOf(".cpp"));
            builder.command("g++", file.getAbsolutePath(), "-o", "./tmp/exec/" + outFileName);
            try {
                Process process = builder.start();
                process.waitFor(5, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            File outFile = new File("./tmp/exec/" + outFileName);
            /*if(!outFile.exists()) {
               TODO: 生成 crashReport
            }*/
            execFiles.add(outFile);
        }
        return execFiles;
    }
}
