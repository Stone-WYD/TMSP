package com.njxnet.service.tmsp.utils;

public class CommonUtil {

    public static boolean isEmpty(String s){
        if (s == null || s.isEmpty()) return true;
        return false;
    }

    public static boolean isNotEmpty(String s){
        if (s == null || s.isEmpty()) return false;
        return true;
    }
}
