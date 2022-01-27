package com.wxj.jvm.chapter08;

/**
 * Author: wangxuejiao
 * Date: 2021/12/16 20:20
 * Description:
 **/
public class HeapSpaceInitial {
    public static void main(String[] args) {
        // 返回 java 虚拟机中的内存总量
        long initialMemory = Runtime.getRuntime().totalMemory() / 1024 / 1024;
        // 返回 java 虚拟机试图使用的最大堆内存量
        long maxMemory = Runtime.getRuntime().maxMemory() / 1024 / 1024;

        System.out.println("-Xms： " + initialMemory + "M");
        System.out.println("-Xmx：" + maxMemory + "M");

        System.out.println("系统内存大小为: " + initialMemory * 64.0 / 1024 + "G");
        System.out.println("系统内存大小为： " + maxMemory * 4.0 / 1024 + "G");
    }
}
