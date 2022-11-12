package com.kekwy.se.data;

public class InputType {
    public String type;
    public Range range;

    public InputType(String type, int begin, int end) {
        this.type = type;
        this.range = new Range(begin, end);
    }

    static class Range {
        public int begin;
        public int end;

        public Range(int begin, int end) {
            this.begin = begin;
            this.end = end;
        }
    }
}
