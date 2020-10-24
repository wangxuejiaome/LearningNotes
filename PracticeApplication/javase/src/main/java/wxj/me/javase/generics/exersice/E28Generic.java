package wxj.me.javase.generics.exersice;

import sun.net.www.content.text.Generic;

/**
 * Copyright (C), 2015-2019, suning
 * FileName: E28Generic
 * Author: wangxuejiao
 * Date: 2019/12/19 20:52
 * Description:
 * Version: 1.0.0
 */

class Generic1<T> {
   public void set(T arg){}
}

class Generic2<T> {

    public T get(){
        return null;
    }
}

public class E28Generic {

    static <T> void f1(Generic1<? super T> obj,T item){
        obj.set(item);
    }
    static <T> T f2(Generic2<? extends T> obj){
        return obj.get();
    }

    public static void main(String[] args) {
        Generic1<Fruit> generic1 = new Generic1<>();
        f1(generic1,new Apple("apple"));
    }
}
