package wxj.me.javase.generics.demostration;

/**
 * Copyright (C), 2015-2019, suning
 * FileName: LinkedStack
 * Author: wangxuejiao
 * Date: 2019/10/17 17:25
 * Description:
 * Version: 1.0.0
 */
public class LinkedStack<T> {


    private static class Node<D> {
        D data;
        Node<D> next;

        Node() {
            data = null;
            next = null;
        }

        Node(D data, Node<D> next) {
            this.data = data;
            this.next = next;
        }

        boolean end() {
            return data == null && next == null;
        }
    }

    // End sentinel
    private Node<T> top = new Node<>();

    public void push(T item){
        top = new Node<>(item, top);
    }

}
