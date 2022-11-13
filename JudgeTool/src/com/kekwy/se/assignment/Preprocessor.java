package com.kekwy.se.assignment;

import java.net.URL;
import java.util.List;

public interface Preprocessor {
    List<URL> preprocess(List<URL> sourceCode);
}
