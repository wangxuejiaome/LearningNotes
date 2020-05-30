package wxj.me.javase.generics.demostration;

/**
 * Copyright (C), 2015-2019, suning
 * FileName: GenericArray
 * Author: wangxuejiao
 * Date: 2019/12/2 19:19
 * Description:
 * Version: 1.0.0
 */
public class GenericArray<T> {

    private T[] array;
    public GenericArray(int size){
        array = (T[]) new Object[size];
    }

    public void put(int index,T item){
        array[index] = item;
    }

    public T get(int index) {
        return array[index];
    }

    // Method that exposes the underlying representation
    public T[] rep() {
        return array;
    }

    public static void main(String[] args) {
        GenericArray<Integer> gai = new GenericArray<>(10);
        // This cause a ClassCastException:
        // Integer[] ia = gai.rep();
//        Object[] oa = gai.rep();
    }
}
