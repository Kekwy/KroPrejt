package com.kekwy.se.data;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class SourceCodeGroup implements Loadable{
    private final List<File> fileList;
    private final String language;
    private final List<InputType> inputTypes = new ArrayList<>();

    private final static Pattern inputInfoPattern = Pattern.compile("");

    public SourceCodeGroup(List<File> fileList, String language, File inputInfo){
        this.fileList = fileList;
        this.language = language;
    }

    public SourceCodeGroup(List<File> fileList, String language, String inputInfo){
        this.fileList = fileList;
        this.language = language;
    }

    public String getLanguage() {
        return language;
    }

    public List<InputType> getInputType() {
        return inputTypes;
    }

    public List<File> getFileList() {
        return fileList;
    }
}
