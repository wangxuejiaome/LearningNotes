package wxj.me.javase.generics.demostration;

import wxj.me.javase.util.Print;

/**
 * Copyright (C), 2015-2019, suning
 * FileName: InstantiateGenericType
 * Author: wangxuejiao
 * Date: 2019/11/28 19:06
 * Description:
 * Version: 1.0.0
 */

class ClassAsFactory<T> {
    T x;
    public ClassAsFactory(Class<T> kind){
        try {
            x = kind.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

class Employee {}

public class InstantiateGenericType {

    public static void main(String[] args){
        ClassAsFactory<Employee> fe = new ClassAsFactory<>(Employee.class);
        System.out.println("ClassAsFactory<Employee> succeeded");
        try {
            ClassAsFactory<Integer> fi = new ClassAsFactory<>(Integer.class);
        }catch (Exception e) {
            System.out.println("ClassAsFactory<Integer> failed");
        }
    }
}
