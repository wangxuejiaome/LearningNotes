package wxj.me.javase.generics.exersice;

/**
 * Copyright (C), 2015-2019, suning
 * FileName: E26Generic
 * Author: wangxuejiao
 * Date: 2019/12/18 17:01
 * Description:
 * Version: 1.0.0
 */
public class E26Generic {

    public static void main(String[] args) {
        Number[] numbers = new Integer[10];
        numbers[0] = 1; // OK
        // Runtime type is Integer[], not Float[] or Byte[]:
        try {
            // Compiler allows you to add Float:
            numbers[1] = new Float(1.0); // ArrayStoreException
        } catch (Exception e) {
            System.out.println(e);
        }
        try {
            // Compiler allows you to add Byte:
            numbers[2] = Byte.valueOf((byte)1);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
