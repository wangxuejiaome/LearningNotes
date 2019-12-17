package wxj.me.javase.generics.demostration;

/**
 * Copyright (C), 2015-2019, suning
 * FileName: FactoryConstraint
 * Author: wangxuejiao
 * Date: 2019/11/28 19:38
 * Description:
 * Version: 1.0.0
 */

interface FactoryI<T> {
    T create();
}

class Foo2<T> {
    private T x;
    public <F extends FactoryI<T>> Foo2(F factory){
        x = factory.create();
    }
}

class IntegerFactory implements FactoryI<Integer> {

    @Override
    public Integer create() {
        return 0;
    }
}

class Widget {
    public static class Factory implements FactoryI<Widget> {

        @Override
        public Widget create() {
            return new Widget();
        }
    }
}


public class FactoryConstraint {
    public static void main(String[] args){
        new Foo2<>(new IntegerFactory());
        new Foo2<>(new Widget.Factory());
    }
}
