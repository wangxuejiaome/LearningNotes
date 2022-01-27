package com.wxj.jvm.chapter08;

/**
 * Author: wangxuejiao
 * Date: 2021/12/13 20:17
 * Description:  -Xms10m -Xmx10m
 **/
public class HeapDemo {
    public static void main(String[] args) {
        System.out.println("start...");
        try {
            Thread.sleep(1000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("end...");
    }
}
