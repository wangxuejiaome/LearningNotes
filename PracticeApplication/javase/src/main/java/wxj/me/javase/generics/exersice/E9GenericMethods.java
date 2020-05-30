package wxj.me.javase.generics.exersice;

/**
 * Copyright (C), 2015-2019, suning
 * FileName: E9GenericMethods
 * Author: wangxuejiao
 * Date: 2019/10/18 16:06
 * Description:
 * Version: 1.0.0
 */
public class E9GenericMethods {

    private <X,Y,Z> void f(X x, Y y, Z z) {
        System.out.println(x.getClass().getName() + ";" + y.getClass().getName() + ";" + z.getClass().getName());
    }

    public static void main(String[] args) {
        E9GenericMethods genericMethods = new E9GenericMethods();
        genericMethods.f("",1,1.0);
    }
}
