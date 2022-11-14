package com.kekwy.se;

import com.kekwy.se.assignment.Executor;

import java.io.File;
import java.io.IOException;

public class CppExecutor implements Executor {

    private final ProcessBuilder builder = new ProcessBuilder();
    @Override
    public Process exec(File execFile) {

        builder.command(execFile.getAbsolutePath());
        builder.redirectInput(ProcessBuilder.Redirect.PIPE);
        builder.redirectOutput(ProcessBuilder.Redirect.PIPE);
        Process process;
        try {
            process = builder.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return process;
    }
}
