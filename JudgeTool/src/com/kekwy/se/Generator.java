package com.kekwy.se;

import com.kekwy.se.data.InputType;

import java.net.URL;
import java.util.List;

public interface Generator {
    List<URL> generate(List<InputType> inputTypes);
}
