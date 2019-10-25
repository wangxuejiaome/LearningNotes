package wxj.me.javase.generics.exersice;

import wxj.me.javase.util.Generator;
import wxj.me.javase.util.Tuple;

/**
 * Copyright (C), 2015-2019, suning
 * FileName: E14Generator
 * Author: wangxuejiao
 * Date: 2019/10/21 17:09
 * Description:
 * Version: 1.0.0
 */
public class E14Generator {

    public static void main(String[] args) {
        Generator<CountedObject> generator = new BasicGenerator<>(CountedObject.class);
        for (int i = 0; i <4; i++) {
            System.out.println(generator.next());
        }
    }
}

class BasicGenerator<T> implements Generator<T> {

    private Class<T> type;

    public BasicGenerator(Class<T> type) {
        this.type = type;
    }

    @Override
    public T next() {
        try {
            // Assume type is a pubic class
            return type.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Produce a Default generator given a type toke:
    public static <T> Generator<T> create(Class<T> type) {
        return new BasicGenerator<>(type);
    }
}

class CountedObject {
    private static long counter = 0;
    private final long id = counter++;

    public long id() {
        return id;
    }

    @Override
    public String toString() {
        return "CountedObject " + id;
    }
}