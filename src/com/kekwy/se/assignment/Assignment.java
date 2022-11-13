package com.kekwy.se.assignment;

public interface Assignment {
    int getThreadNum();
    boolean isFinished();
    void launch(int n);
    void create();
}
