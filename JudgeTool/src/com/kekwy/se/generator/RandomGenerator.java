package com.kekwy.se.generator;

import com.kekwy.se.payload.InputInfo;

import java.io.*;
import java.util.List;
import java.util.Random;

/**
 * 基于随机生成方式的生成器
 */
public class RandomGenerator implements Generator {
    public static final int NUMBER_OF_SAMPLES = 100;
    public static final String OUTPUT_PATH = "./tmp/input/";
    private static final File OUTPUT_DIRECTORY = new File(OUTPUT_PATH);
    private final Random random = new Random();

    static {
        if (!OUTPUT_DIRECTORY.exists()) {
            if (!OUTPUT_DIRECTORY.mkdirs()) {
                throw new RuntimeException("输出路径创建失败");
            }
        }
    }

    @Override
    public File generate(List<InputInfo> inputInfoList) throws IOException {
        BufferedOutputStream bfOs;
        File sampleFile;
        sampleFile = File.createTempFile("judge", ".tmp", OUTPUT_DIRECTORY);  // 生成临时文件
        bfOs = new BufferedOutputStream(new FileOutputStream(sampleFile));
        for (int i = 0; i < NUMBER_OF_SAMPLES; i++) {
            for (InputInfo inputInfo : inputInfoList) {
                switch (inputInfo.type) {
                    case "INT" -> {
                        int parameter = inputInfo.range.begin +
                                random.nextInt(inputInfo.range.end + 1 - inputInfo.range.begin);
                        bfOs.write(Integer.toString(parameter).getBytes());
                    }
                    case "STRING" -> {
                        // TODO 使用指定字符集生成指定长度的随机字符串
                    }
                    default -> {
                        // TODO 提示用户/管理员当前生成器不支持该类型
                    }
                }
                bfOs.write((" ").getBytes());
            }
            bfOs.write(("\n").getBytes());
        }
        bfOs.flush();
        bfOs.close();
        return sampleFile;
    }
}
