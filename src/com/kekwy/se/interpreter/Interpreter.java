package com.kekwy.se.interpreter;

import com.kekwy.se.data.DataStruct;

import java.util.List;

public interface Interpreter {
    List<DataStruct> interpret(String command);
}
