package com.kekwy.se.assignment;

import com.kekwy.se.data.InputType;
import java.io.File;
import java.io.IOException;
import java.util.List;

public interface Generator {
    File generate(List<InputType> inputTypes) throws IOException;
}
