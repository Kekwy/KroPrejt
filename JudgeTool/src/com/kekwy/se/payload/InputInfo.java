package com.kekwy.se.payload;

import java.util.Locale;

/**
 * 待测 OJ 程序的输入参数类型以及范围
 */
public class InputInfo {
    public String type;           // 类型
    public Range range;           // 范围

    public InputInfo(String type, int begin, int end) {
        this.type = type.toUpperCase(Locale.ROOT);
        this.range = new Range(begin, end);
    }

    public static class Range {
        public int begin;
        public int end;

        public Range(int begin, int end) {
            this.begin = begin;
            this.end = end;
        }
    }

}
