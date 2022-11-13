package com.kekwy.se.assignment;

import com.kekwy.se.data.InputType;

import java.io.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class JudgeAssignment implements Runnable {

    private static final Map<String, List<String>> compilerCmdMap = new HashMap<>();
    private static final Map<String, Compiler> compilerMap = new HashMap<>();

    private final ProcessBuilder builder = new ProcessBuilder();

    private List<File> codeFiles;

    private Generator generator;
    List<InputType> types;

    String language;

    Compiler compiler;

    List<String> executeCmd;

    public JudgeAssignment(List<File> codeFiles, String language, List<InputType> types) {

    }


    Executor executor;

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
            File dir = new File("./tmp");
            if(!dir.exists()) {
                if (!dir.mkdir()) {
                    throw new RuntimeException("目录创建失败");
                }
            }
            File tmp = File.createTempFile("judge",".tmp", new File("./tmp"));
            BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(tmp));
            String input = reader.readLine();              // 从数据集文件中读出一行
            for (; input != null; input = reader.readLine()) {
                Process process = executor.exce(execFile); // 启动待测程序
                BufferedOutputStream bfos = new BufferedOutputStream(process.getOutputStream());
                bfos.write((input + "\n").getBytes());     // 向目标进程的输入测试用例
                bfos.flush();
                bfos.close();
                boolean isTimeout;
                try {                                      // 等待进程执行结束
                    isTimeout = !(process.waitFor(5, TimeUnit.SECONDS));
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

    @Override
    public void run() {
        work();

    }


    List<String[]> result;

    /**
     * 等价性判断任务的工作流程
     */
    private void work() {
        try {
            File inputFile = generator.generate(types);            // 生成数据集
            List<File> execFiles = compiler.compile(codeFiles);    // 编译源代码
            List<File> outputFiles = exec(execFiles, inputFile);   // 执行程序，保存输出
            result = compare(outputFiles);                         // 对比输出结果，划分等价对
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<String[]> compare(List<File> outputFiles) {
        //TODO
        return null;
    }
}
