package com.kekwy.se.assignment;

import com.kekwy.se.compiler.Compiler;
import com.kekwy.se.executor.Executor;
import com.kekwy.se.generator.Generator;
import com.kekwy.se.generator.RandomGenerator;
import com.kekwy.se.payload.InputInfo;
import com.kekwy.se.payload.ProgramPairs;

import java.io.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class JudgeAssignment extends Assignment<ProgramPairs> implements Runnable {

    private static final Map<String, Executor> ExecutorMap = new HashMap<>();
    private static final Map<String, Compiler> compilerMap = new HashMap<>();

    public static void addExecutor(Executor executor, String language) {
        ExecutorMap.put(language.toLowerCase(Locale.ROOT), executor);
    }

    public static void addCompiler(Compiler compiler, String language) {
        compilerMap.put(language.toLowerCase(Locale.ROOT), compiler);
    }

    private static Generator generator = new RandomGenerator();

    public static void setGenerator(Generator generator) {
        JudgeAssignment.generator = generator;
    }

    private final List<File> codeFiles;
    private final Compiler compiler;
    private final Executor executor;
    private final List<InputInfo> types;


    public JudgeAssignment(List<File> codeFiles, String language, List<InputInfo> types) {
        this.codeFiles = codeFiles;
        this.compiler = compilerMap.get(language.toLowerCase(Locale.ROOT));
        this.executor = ExecutorMap.get(language.toLowerCase(Locale.ROOT));
        this.types = types;
        File dir = new File("./tmp/output/");
        if(!dir.exists()) {
            if (!dir.mkdir()) {
                throw new RuntimeException("目录创建失败");
            }
        }
    }

    /**
     * 使用指定的输入数据集测试所有程序，并返回保存每个程序输出的文件列表
     * @param execFiles 待测的程序列表
     * @param inputFile 输入数据集文件
     * @return 所有待测程序产生的输出文件的列表
     * @throws IOException IO异常
     */
    private List<File> exec(List<File> execFiles, File inputFile) throws IOException {
        List<File> outputFiles = new ArrayList<>();
        for (File execFile : execFiles) {
            BufferedReader reader;
            try {
                reader = new BufferedReader(new FileReader(inputFile));
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            // 生成一个临时文件, 用于保存目标进程的输出
            File tmp = File.createTempFile("judge",".tmp", new File("./tmp/output"));
            BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(tmp));
            String input = reader.readLine();              // 从数据集文件中读出一行
            for (; input != null; input = reader.readLine()) {
                Process process = executor.exec(execFile); // 启动待测程序
                BufferedOutputStream bfos = new BufferedOutputStream(process.getOutputStream());
                bfos.write((input + "\n").getBytes());     // 向目标进程的输入测试用例
                bfos.flush();
                bfos.close();
                boolean isTimeout;
                try {                                      // 等待进程执行结束
                    isTimeout = !(process.waitFor(2, TimeUnit.SECONDS));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                if (isTimeout) {
                    process.destroy();                     // 超时强制杀死进程
                } else {
                    // 获取目标进程标准输出的输入流
                    BufferedInputStream stdout = new BufferedInputStream(process.getInputStream());
                    output.write(stdout.readAllBytes());
                    stdout.close();
                }
            }
            output.close();
            outputFiles.add(tmp);
            reader.close();
        }
        return outputFiles;
    }

    /**
     * 等价性判断任务的工作流程
     */
    @Override
    public ProgramPairs work() {
        try {
            File inputFile = generator.generate(types);            // 生成数据集
            List<File> execFiles = compiler.compile(codeFiles);    // 编译源代码
            List<File> outputFiles = exec(execFiles, inputFile);   // 执行程序，保存输出
            ProgramPairs result = compare(outputFiles);            // 对比输出结果，划分等价对
            removeTempFiles(inputFile, execFiles, outputFiles);    // 删除测试过程中产生的临时文件
            return result;                                         // 返回执行结果
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 移除测试过程中动态生成的临时文件
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void removeTempFiles(File inputFile, List<File> execFiles, List<File> outputFiles) {
        inputFile.delete();
        for (File execFile : execFiles) {
            execFile.delete();
        }
        for (File outputFile : outputFiles) {
            outputFile.delete();
        }
    }

    private ProgramPairs compare(List<File> outputFiles) throws IOException {
        ProgramPairs programPairs = new ProgramPairs();

        for (int i = 0; i < codeFiles.size(); i++) {
            File output1 = outputFiles.get(i);
            for (int j = i + 1; j < codeFiles.size(); j++) {
                File output2 = outputFiles.get(j);
                BufferedInputStream bfIs1;
                BufferedInputStream bfIs2;
                try {
                    bfIs1 = new BufferedInputStream(new FileInputStream(output1));
                    bfIs2 = new BufferedInputStream(new FileInputStream(output2));
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }

                boolean isEqual = Arrays.equals(bfIs1.readAllBytes(), bfIs2.readAllBytes());
                bfIs1.close();
                bfIs2.close();
                if (isEqual) {
                    programPairs.addEqualPair(codeFiles.get(i), codeFiles.get(j));
                } else {
                    programPairs.addInequalPair(codeFiles.get(i), codeFiles.get(j));
                }

            }
        }
        return programPairs;
    }
}