import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class Main {

    public List<File> compile(List<File> sourceCode) {
        ProcessBuilder builder = new ProcessBuilder();
        builder.inheritIO();
        File outputPath = new File("./tmp/exec/");
        if(!(outputPath.exists() && outputPath.isDirectory())) {
            if(!outputPath.mkdirs()) {
                throw new RuntimeException("编译输出路径不存在且创建失败");
            }
        }
        List<File> execFiles = new ArrayList<>();
        for (File file : sourceCode) {
            String sourceName = file.getName();
            String prefix = UUID.randomUUID().toString().trim().replaceAll("-", "");
            String outFileName = prefix + sourceName.substring(0, sourceName.indexOf(".cpp"));
            builder.command("g++", file.getAbsolutePath(), "-o", "./tmp/exec/" + outFileName);
            try {
                Process process = builder.start();
                process.waitFor(5, TimeUnit.SECONDS);
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
            File outFile = new File("./tmp/exec/" + outFileName);
            /*if(!outFile.exists()) {
               TODO: 生成 crashReport
            }*/
            execFiles.add(outFile);
        }
        return execFiles;
    }

    public static void main(String[] args) throws IOException {
        List<File> list = new ArrayList<>();
        list.add(new File("/Users/kekwy/Desktop/test.cpp"));
        list.add(new File("/Users/kekwy/Desktop/test1.cpp"));
        // new Main().compile(list);
        BufferedInputStream bfIs1;
        BufferedInputStream bfIs2;
        try {
            bfIs1 = new BufferedInputStream(new FileInputStream(list.get(0)));
            bfIs2 = new BufferedInputStream(new FileInputStream(list.get(1)));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        System.out.println(Arrays.equals(bfIs1.readAllBytes(), bfIs2.readAllBytes()));
        bfIs1.close();
        bfIs2.close();
    }
}

// prejt 