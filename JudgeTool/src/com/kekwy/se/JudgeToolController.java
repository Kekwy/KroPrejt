package com.kekwy.se;

import com.kekwy.se.assignment.Assignment;
import com.kekwy.se.assignment.JudgeAssignment;
import com.kekwy.se.compiler.CppCompiler;
import com.kekwy.se.executor.CppExecutor;
import com.kekwy.se.payload.ProgramPairs;
import com.kekwy.se.payload.SourceCodeGroup;

import java.util.ArrayList;
import java.util.List;

public class JudgeToolController {

    static {
        JudgeAssignment.addCompiler(new CppCompiler(), "c");
        JudgeAssignment.addExecutor(new CppExecutor(), "c");
        JudgeAssignment.addCompiler(new CppCompiler(), "cpp");
        JudgeAssignment.addExecutor(new CppExecutor(), "cpp");
    }
    private final AssignmentManager<ProgramPairs> assignmentManager = new AssignmentManager<>();

    public void createAssignments(List<SourceCodeGroup> sourceCodeGroups) {
        List<Assignment<ProgramPairs>> assignmentList = new ArrayList<>();
        for (SourceCodeGroup sourceCodeGroup : sourceCodeGroups) {
            assignmentList.add(new JudgeAssignment(sourceCodeGroup.getFileList(),
                    sourceCodeGroup.getLanguage(), sourceCodeGroup.getInputType()));
        }
        assignmentManager.postAssignment(assignmentList);
    }

    public List<ProgramPairs> runAssignments() {
        assignmentManager.launch();
        return assignmentManager.waitForData();
    }

}
