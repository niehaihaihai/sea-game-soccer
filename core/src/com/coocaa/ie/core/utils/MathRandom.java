package com.coocaa.ie.core.utils;

public class MathRandom {
    public static final int random(int start, int end) {
        return (int) (Math.random() * (end - start)) + start;
    }
}
