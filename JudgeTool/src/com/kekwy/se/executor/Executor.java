package com.kekwy.se.executor;

import java.io.File;
import java.io.IOException;

/**
 * 执行器接口：
 * <p>
 * 输入可执行文件，根据其实现生成一个进程，并返回该进程对象
 */
public abstract class Executor {
    /**
     * 执行输入文件，并返回生成的进程对象
     * @param execFile 期望运行的可执行文件
     * @return 生成的进程对象
     * @throws IOException IO异常
     */
    public abstract Process exec(File execFile, File inputFile, File outputFile) throws IOException;
}
