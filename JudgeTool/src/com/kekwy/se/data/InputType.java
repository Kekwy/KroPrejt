package com.kekwy.se.data;

public class InputType {
    public Type type;
    public Range range;

    public InputType(Type type, int begin, int end) {
        this.type = type;
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

    public enum Type {
        TYPE_INT,
        TYPE_STRING,
    }

}
