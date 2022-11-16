import com.kekwy.se.CppCompiler;
import com.kekwy.se.CppExecutor;
import com.kekwy.se.JudgeAssignment;
import com.kekwy.se.assignment.AssignmentManager;
import com.kekwy.se.data.InputType;

import java.io.*;
import java.util.*;

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

        /*AssignmentManager<List<List<File[]>>> manager = new AssignmentManager<>();

        new Thread(manager).start();

        manager.postAssignment(judgeAssignment);*/

        // List<List<File[]>> res = manager.waitForData();

        // System.out.println(res.toString());
/*
        List<Integer> integers = new LinkedList<>();


        integers.add(1);
        integers.add(2);
        integers.add(3);

        ListIterator<Integer> iterator = integers.listIterator();

        System.out.println(iterator.next());

        System.out.println(iterator.next());

        iterator.add(4);

        System.out.println(iterator.next());

        iterator = integers.listIterator();

        System.out.println(iterator.next());

        System.out.println(iterator.next());

        System.out.println(iterator.next());

        System.out.println(iterator.next());
*/

    }
}