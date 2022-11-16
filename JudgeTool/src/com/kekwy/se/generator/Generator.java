package com.kekwy.se.generator;

import com.kekwy.se.payload.InputInfo;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * 测试数据集生成器：
 * <p>
 * 使用特定算法实现该接口中的 generate 方法，以完成测试数据集的生成
 */
public interface Generator {
    /**
     * 根据指定的输入参数类型与范围生成测试数据集
     * @param inputInfoList 待测程序输入参数的类型以及范围
     * @return 保存生成的数据集的文件
     * @throws IOException IO异常
     */
    File generate(List<InputInfo> inputInfoList) throws IOException;
}
