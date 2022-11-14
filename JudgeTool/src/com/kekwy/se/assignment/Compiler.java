package com.kekwy.se.assignment;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface Compiler {
    List<File> compile(List<File> sourceCode) throws IOException;
}
