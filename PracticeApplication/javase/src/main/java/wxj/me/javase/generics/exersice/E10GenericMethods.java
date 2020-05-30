package wxj.me.javase.generics.exersice;

/**
 * Copyright (C), 2015-2019, suning
 * FileName: E9GenericMethods
 * Author: wangxuejiao
 * Date: 2019/10/18 16:06
 * Description:
 * Version: 1.0.0
 */
public class E10GenericMethods {

    private <X,Y> void f(X x, Y y, Double z) {
        System.out.println(x.getClass().getName() + ";" + y.getClass().getName() + ";" + z.getClass().getName());
    }

    public static void main(String[] args) {
        E10GenericMethods genericMethods = new E10GenericMethods();
        genericMethods.f("",1,1.0);
    }
}
