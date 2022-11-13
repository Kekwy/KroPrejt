package com.kekwy.se.assignment;

import com.kekwy.se.data.InputType;
import java.io.File;
import java.util.List;

public interface Generator {
    File generate(List<InputType> inputTypes);
}
