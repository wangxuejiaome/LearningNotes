package wxj.me.javase.generics.exersice;

/**
 * Copyright (C), 2015-2019, suning
 * FileName: E25Generic
 * Author: wangxuejiao
 * Date: 2019/12/17 17:25
 * Description:
 * Version: 1.0.0
 */

interface Top {
    void a();
    void b();
}

interface Low {
    void c();
    void d();
}

class TopLowImpl implements Top,Low {

    @Override
    public void a() {
        System.out.println("Top::a()");
    }

    @Override
    public void b() {
        System.out.println("Top::b()");
    }

    @Override
    public void c() {
        System.out.println("Low::c()");
    }

    @Override
    public void d() {
        System.out.println("Low::d()");
    }
}


public class E25Generic {

    static <T extends Top> void top(T obj){
        obj.a();
        obj.b();
    }

    static <T extends Low> void low(T obj) {
        obj.c();
        obj.d();
    }

    public static void main(String[] args) {
        TopLowImpl tli = new TopLowImpl();
        top(tli);
        low(tli);
    }
}
