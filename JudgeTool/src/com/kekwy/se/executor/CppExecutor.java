package com.kekwy.se.executor;

import java.io.File;
import java.io.IOException;

/**
 * 执行 C/Cpp 编程生成的可执行文件
 */
public class CppExecutor implements Executor {

    @Override
    public Process exec(File execFile, File inputFile, File outputFile) throws IOException {
        ProcessBuilder builder = new ProcessBuilder();
        builder.command(execFile.getAbsolutePath());       // 设置运行指令
        builder.redirectInput(inputFile);                  // 重定向进程标准输入到文件
        builder.redirectOutput(ProcessBuilder.Redirect.appendTo(outputFile)); // 重定向进程标准输出到文件
        return builder.start();         // 启动进程并返回进程对象
    }
}
