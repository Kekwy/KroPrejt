package com.kekwy.se.executor;

import java.io.File;
import java.io.IOException;

/**
 * 执行 C/Cpp 编程生成的可执行文件
 */
public class CppExecutor implements Executor {

    private final ProcessBuilder builder = new ProcessBuilder();
    @Override
    public Process exec(File execFile) throws IOException {
        builder.command(execFile.getAbsolutePath());            // 设置运行指令
        builder.redirectInput(ProcessBuilder.Redirect.PIPE);    // 重定向进程标准输入到管道
        builder.redirectOutput(ProcessBuilder.Redirect.PIPE);   // 重定向进程标准输出到管道
        return builder.start();         // 启动进程并返回进程对象
    }
}
