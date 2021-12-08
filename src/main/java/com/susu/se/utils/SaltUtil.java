package com.susu.se.utils;

import java.util.Random;

public class SaltUtil {
    public static String getSalt(int n){
        char[] chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz01234567890!@#$%^&*()".toCharArray();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            char aChar = chars[new Random().nextInt(chars.length)];
            sb.append(aChar);
        }
        //返回n位随机盐
        return sb.toString();

    }

//    public static void main(String[] args) {
//        String salt = getSalt(8);
//        System.out.println(salt);
//    }
}
