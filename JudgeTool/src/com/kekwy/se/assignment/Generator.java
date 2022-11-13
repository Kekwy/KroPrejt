package com.kekwy.se.assignment;

import com.kekwy.se.data.InputType;

import java.net.URL;
import java.util.List;

public interface Generator {
    URL generate(List<InputType> inputTypes);
}
