package wxj.me.javase.generics.exersice;

import java.lang.reflect.Constructor;

/**
 * Copyright (C), 2015-2019, suning
 * FileName: E22Generic
 * Author: wangxuejiao
 * Date: 2019/12/2 11:42
 * Description:
 * Version: 1.0.0
 */

class ClassAsFactory<T> {
    Class<T> kind;
    public ClassAsFactory(Class<T> kind){
        this.kind = kind;
    }
    public T create(int arg) {
        try {
            // Technique 1 (verbose)
            for (Constructor<?> ctor: kind.getConstructors()) {
                // Look for a constructor whit a single parameter
                Class<?>[] params = ctor.getParameterTypes();
                if(params.length == 1)
                    if(params[0] == int.class)
                        return kind.cast(ctor.newInstance(arg));
            }
             // Technique 2 (direct)
             // Constructor<T> ct = kind.getConstructor(int.class);
             // return ct.newInstance(arg);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}

public class E22Generic {
    public static void main(String[] args) {
       /* ClassAsFactory<Employee> fe = new ClassAsFactory<>(Employee.class);
        Employee emp = fe.create(1);
        if(emp == null)
            System.out.println("Employee cannot be instantiated!");*/
        ClassAsFactory<Integer> fi = new ClassAsFactory<>(Integer.class);
        Integer i = fi.create(0);
        if(i == null)
            System.out.println("Integer cannot be instantiated!");
    }
}
