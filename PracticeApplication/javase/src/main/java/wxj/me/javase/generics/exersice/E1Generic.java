package wxj.me.javase.generics.exersice;

/**
 * Copyright (C), 2015-2019, suning
 * FileName: E1Generic
 * Author: wangxuejiao
 * Date: 2019/10/17 14:48
 * Description:
 * Version: 1.0.0
 */
public class E1Generic<T> {
    private T a;

    private E1Generic(T a) {
        this.a = a;
    }

    public void set(T a) {
        this.a = a;
    }

    public T get() {
        return this.a;
    }

    public static void main(String[] args) {
        E1Generic<Fruit> genericHolder = new E1Generic<>(new Fruit("fruit"));
        Fruit fruit = genericHolder.get();
        System.out.println(fruit.name);
        genericHolder.set(new Apple("apple"));
        Fruit apple = genericHolder.get();
        System.out.println(apple.name);
    }
}


class Fruit {

    String name;

    Fruit(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

class Apple extends Fruit {

    Apple(String name) {
        super(name);
    }
}

class Banana extends Fruit {

    public Banana(String name) {
        super(name);
    }
}