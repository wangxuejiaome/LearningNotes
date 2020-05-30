package wxj.me.javase.generics.exersice;

import wxj.me.javase.util.FiveTuple;
import wxj.me.javase.util.FourTuple;
import wxj.me.javase.util.ThreeTuple;
import wxj.me.javase.util.TwoTuple;

import static wxj.me.javase.util.Tuple.tuple;

/**
 * Copyright (C), 2015-2019, suning
 * FileName: E15Generic
 * Author: wangxuejiao
 * Date: 2019/10/22 17:41
 * Description:
 * Version: 1.0.0
 */
public class E15Generic {

    static TwoTuple<String,Integer> f() {
        return tuple("hi",47);
    }

    static TwoTuple f2() {
        return tuple("hi",47);
    }

    static ThreeTuple<Fruit,String,Integer> g() {
        return tuple(new Fruit("fruit"),"hi",47);
    }

    static FourTuple<Person,Fruit,String,Integer> h() {
        return tuple(new Person("xxn",1),new Fruit("fruit"),"hi",47);
    }

    static FiveTuple<Boolean,Person,Fruit,String,Integer> k() {
        return tuple(false,new Person("xxn",1),new Fruit("fruit"),"hi",47);
    }

    public static void main(String[] args) {
        TwoTuple<String,Integer> ttsi = f();
        System.out.println(ttsi);
        TwoTuple<String,Integer> ttsi2 = f2();
        System.out.println(ttsi2);
        System.out.println(g());
        System.out.println(h());
        System.out.println(k());
    }
}
