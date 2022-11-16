package com.kekwy.se.compiler;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * 编译器接口，用于进行相应语言源代码的编译
 * <p>
 * 实现该类中的 compile 方法，以支持对某一编程语言源程序的编译
 */
public interface Compiler {
    /**
     * 运行编译器，对所有传入的源码文件进行编译，返回对应的可执行文件
     * @param sourceCode 待编译的所有源代码文件
     * @return 每个源代码文件对应的编译生成文件
     * @throws IOException IO异常
     */
    List<File> compile(List<File> sourceCode) throws IOException;
}
