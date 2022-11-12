package com.kekwy.se;

import java.net.URL;
import java.util.List;

public interface Preprocessor {
    List<URL> preprocess(List<URL> sourceCode);
}
