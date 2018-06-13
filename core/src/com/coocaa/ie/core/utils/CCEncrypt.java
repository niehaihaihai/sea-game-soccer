package com.coocaa.ie.core.utils;

public class CCEncrypt {
    public static final byte[] encrypt(byte[] bytes, String key) {
        byte[] result = new byte[bytes.length];
        int _key = key.hashCode();//Integer.valueOf(key.hashCode()).byteValue();
        for (int i = 0; i < bytes.length; i++) {
            result[i] = (byte) (bytes[i] ^ _key);
        }
        return result;
    }

    public static final byte[] decrypt(byte[] bytes, String key) {
        byte[] result = new byte[bytes.length];
        int _key = key.hashCode();//Integer.valueOf(key.hashCode()).byteValue();
        for (int i = 0; i < bytes.length; i++) {
            result[i] = (byte) (bytes[i] ^ _key);
        }
        return result;
    }
}
