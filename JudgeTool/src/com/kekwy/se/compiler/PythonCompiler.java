package com.kekwy.se.compiler;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Python 不需要编译器，此处仅为保证处理流程一致
 */
public class PythonCompiler extends Compiler {
    /**
     * 不处理，直接返回
     */
    @Override
    public List<File> compile(List<File> sourceCode) throws IOException {
        return sourceCode;
    }
}
