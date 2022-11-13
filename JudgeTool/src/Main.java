import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Main {

    public interface Executor {
        Process exce(File execFile);
    }

    Executor executor = execFile -> {
        ProcessBuilder builder = new ProcessBuilder();
        builder.command(execFile.getAbsolutePath());
        // builder.inheritIO();
        builder.redirectInput(ProcessBuilder.Redirect.PIPE);
        builder.redirectOutput(ProcessBuilder.Redirect.PIPE);
        try {
            return builder.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    };

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
                bfos.write((input + "\n").getBytes());              // 向目标进程的输入测试用例
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
    public static void main(String[] args) throws IOException {
        List<File> list = new ArrayList<>();
        list.add(new File("/Users/kekwy/Desktop/test"));
        list.add(new File("/Users/kekwy/Desktop/test1"));
        new Main().exec(list, new File("/Users/kekwy/Desktop/input.txt"));
    }
}

// prejt 