package com.kekwy.se;

import com.kekwy.se.assignment.Generator;
import com.kekwy.se.data.InputType;

import java.io.*;
import java.util.List;
import java.util.Random;

public class RandomGenerator implements Generator {

    public static final int NUMBER_OF_SAMPLES = 100;

    public static final String OUTPUT_PATH = "./tmp/input";

    private final File outputDir = new File(OUTPUT_PATH);

    private final Random random = new Random();

    RandomGenerator() {
        if (!outputDir.exists()) {
            if (!outputDir.mkdirs()) {
                throw new RuntimeException("输出路径创建失败");
            }
        }
    }

    @Override
    public File generate(List<InputType> inputTypes) throws IOException {
        BufferedOutputStream bfOs;
        File sampleFile;
        sampleFile = File.createTempFile("judge", ".tmp", outputDir);
        bfOs = new BufferedOutputStream(new FileOutputStream(sampleFile));
        for (int i = 0; i < NUMBER_OF_SAMPLES; i++) {
            for (InputType inputType : inputTypes) {
                switch (inputType.type) {
                    case TYPE_INT -> {
                        int parameter = inputType.range.begin +
                                random.nextInt(inputType.range.end + 1 - inputType.range.begin);
                        bfOs.write(Integer.toString(parameter).getBytes());
                    }
                    case TYPE_STRING -> {
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
