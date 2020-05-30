package wxj.me.javase.generics.exersice;

import java.util.Iterator;

import wxj.me.javase.util.Generator;

/**
 * Copyright (C), 2015-2019, suning
 * FileName: E7FibonacciIterable
 * Author: wangxuejiao
 * Date: 2019/10/18 14:42
 * Description:
 * Version: 1.0.0
 */
public class E7FibonacciIterable implements Iterable<Integer> {

    private Fibonacci fibonacci = new Fibonacci();
    private int size;

    public E7FibonacciIterable(int size){
        this.size = size;
    }

    @Override
    public Iterator<Integer> iterator() {
        return new Iterator<Integer>() {
            @Override
            public boolean hasNext() {
                return size > 0;
            }

            @Override
            public Integer next() {
                size--;
                return fibonacci.next();
            }
        };
    }

    public static void main(String[] args) {
        for (Integer integer : new E7FibonacciIterable(18))
            System.out.println(integer);
    }
}


class Fibonacci implements Generator<Integer> {

    private int count = 0;

    @Override
    public Integer next() {
        return fib(count++);
    }

    private int fib(int n) {
        if(n < 2) return 1;
        return fib(n-2) + fib(n-1);
    }
}
