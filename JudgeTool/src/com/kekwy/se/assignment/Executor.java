package com.kekwy.se.assignment;

import java.io.File;
import java.io.IOException;

public interface Executor {
    Process exec(File execFile) throws IOException;
}
