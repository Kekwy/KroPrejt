package com.kekwy.se.compiler;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Deprecated
public class CCompiler extends Compiler {
    private final static String OUTPUT_PATH = "./tmp/exec/c/";
    private final static File OUTPUT_DIRECTORY = new File(OUTPUT_PATH);
    private final ProcessBuilder builder = new ProcessBuilder();

    static {
        if (!(OUTPUT_DIRECTORY.exists() && OUTPUT_DIRECTORY.isDirectory())) {        // 检查临时文件存放路径
            if (!OUTPUT_DIRECTORY.mkdirs()) {                                        // 若不存在则创建
                throw new RuntimeException("编译输出路径不存在且创建失败");
            }
        }
    }

    @Override
    public List<File> compile(List<File> sourceCode) throws IOException {
        List<File> execFiles = new ArrayList<>();              // 可执行文件列表
        for (File file : sourceCode) {
            String sourceName = file.getName();                // 获取文件名，用于生成带有唯一前缀的输出文件名
            String prefix = UUID.randomUUID().toString().trim().replaceAll("-", "");
            String outFileName = prefix + sourceName.substring(0, sourceName.indexOf(".c"));
            builder.command("gcc", file.getAbsolutePath(), "-o", OUTPUT_PATH + outFileName);   // 执行编译命令
            Process process;
            try {
                process = builder.start();
                process.waitFor(2, TimeUnit.SECONDS);   // 等待进程执行完成，若超时则强行杀死进程
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            File outFile = new File(OUTPUT_PATH + outFileName);
            if (!outFile.exists()) {                           // 检查文件是否成功生成
                System.out.println(file.getAbsolutePath() + " 编译失败：");
                // System.out.writeBytes(process.getInputStream().readAllBytes());
                // System.out.flush();
                outFile = null;
            }
            execFiles.add(outFile);
        }
        return execFiles;
    }
}
