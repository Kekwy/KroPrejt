package com.kekwy.se.assignment;

import java.io.File;
import java.util.List;

public interface Compiler {
    List<File> compile(List<File> sourceCode);
}
