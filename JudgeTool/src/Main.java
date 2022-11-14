import com.kekwy.se.CppCompiler;
import com.kekwy.se.CppExecutor;
import com.kekwy.se.JudgeAssignment;
import com.kekwy.se.data.InputType;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        JudgeAssignment.addCompiler(new CppCompiler(), "Cpp");
        JudgeAssignment.addExecutor(new CppExecutor(), "cpp");

        InputType[] types = new InputType[2];
        types[0] = new InputType(InputType.Type.TYPE_INT, 30, 60);
        types[1] = new InputType(InputType.Type.TYPE_INT, 10, 50);
        List<InputType> inputTypes = new ArrayList<>(Arrays.stream(types).toList());

        File[] files = new File[3];
        files[0] = new File("/Users/kekwy/Desktop/test1.cpp");
        files[1] = new File("/Users/kekwy/Desktop/test2.cpp");
        files[2] = new File("/Users/kekwy/Desktop/test3.cpp");
        List<File> codeFiles = new ArrayList<>(Arrays.stream(files).toList());

        JudgeAssignment judgeAssignment = new JudgeAssignment(codeFiles, "Cpp", inputTypes);

        Thread launch = new Thread(judgeAssignment);

        launch.start();

        launch.join();

    }
}