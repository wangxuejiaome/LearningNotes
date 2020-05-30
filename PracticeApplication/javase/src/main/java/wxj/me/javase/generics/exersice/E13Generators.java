package wxj.me.javase.generics.exersice;

import com.sun.jmx.remote.internal.ArrayQueue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;

import wxj.me.javase.util.Generator;

/**
 * Copyright (C), 2015-2019, suning
 * FileName: E13Generators
 * Author: wangxuejiao
 * Date: 2019/10/21 14:49
 * Description:
 * Version: 1.0.0
 */
public class E13Generators {

    public static <T> Collection<T> fill(Collection<T> coll, Generator<T> gen, int n) {
        for (int i = 0; i < n; i++) {
            coll.add(gen.next());
        }
        return coll;
    }

    public static <T> List<T> fill(List<T> list, Generator<T> gen, int n) {
        for (int i = 0; i < n; i++) {
            list.add(gen.next());
        }
        return list;
    }

    public static <T> Queue<T> fill(Queue<T> queue, Generator<T> gen, int n) {
        for (int i = 0; i < n; i++) {
            queue.add(gen.next());
        }
        return queue;
    }

    public static <T> Set<T> fill(Set<T> set, Generator<T> gen, int n) {
        for (int i = 0; i < n; i++) {
            set.add(gen.next());
        }
        return set;
    }

    public static void main(String[] args) {
        Collection<StoryCharacters> collection = fill(new ArrayList<StoryCharacters>(), new StoryCharactersGenerator(), 4);
        for (StoryCharacters s : collection) {
            System.out.print(s + ",");
        }
        System.out.println();

        List<StoryCharacters> list = fill(new ArrayList<StoryCharacters>(), new StoryCharactersGenerator(), 3);
        for (StoryCharacters s : list) {
            System.out.print(s + ",");
        }
        System.out.println();

        Queue<Integer> queue = fill(new ArrayBlockingQueue<Integer>(8), new Fibonacci(), 8);
        for (Integer i : queue) {
            System.out.print(i + ",");
        }
        System.out.println();

        Set <Integer> set = fill(new HashSet<Integer>(),new Fibonacci(),4);
        for (Integer i : set) {
            System.out.print(i + ",");
        }
    }

}
