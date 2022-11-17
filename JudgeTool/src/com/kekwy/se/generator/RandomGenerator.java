package com.kekwy.se.generator;

import com.kekwy.se.payload.InputInfo;

import java.io.*;
import java.util.List;
import java.util.Random;

/**
 * 基于随机生成方式的生成器
 */
public class RandomGenerator implements Generator {
    public static final int NUMBER_OF_SAMPLES = 20;
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

    /**
     * 随机生成大小写字母组成的字符串
     */
    private String generateString(int length) {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < length; i++) {
            if (random.nextInt(2) == 0) {
                str.append((char) (random.nextInt(26) + 'A'));
            } else {
                str.append((char) (random.nextInt(26) + 'a'));
            }
        }
        return str.toString();
    }

    private int generateInt(int begin, int end) {
        return begin + random.nextInt(end + 1 - begin);
    }

    @Override
    public File generate(List<InputInfo> inputInfoList) throws IOException {
        File sampleFile = File.createTempFile("judge", ".tmp", OUTPUT_DIRECTORY);  // 生成临时文件
        BufferedOutputStream bfOs = new BufferedOutputStream(new FileOutputStream(sampleFile));
        for (int i = 0; i < NUMBER_OF_SAMPLES; i++) {
            for (InputInfo inputInfo : inputInfoList) {
                switch (inputInfo.type) {
                    case "INT"    -> {
                        int parameter = generateInt(inputInfo.range.begin, inputInfo.range.end);

                        bfOs.write(Integer.toString(parameter).getBytes());
                    }
                    case "STRING" -> {
                        int length = generateInt(inputInfo.range.begin, inputInfo.range.end);
                        bfOs.write(generateString(length).getBytes());
                    }
                    case "CHAR"   -> {
                        bfOs.write(generateString(1).getBytes());
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
