package wxj.me.javase.generics.demostration;

import java.lang.reflect.Array;

/**
 * Copyright (C), 2015-2019, suning
 * FileName: GenericArrayWithTypeToken
 * Author: wangxuejiao
 * Date: 2019/12/3 9:28
 * Description:
 * Version: 1.0.0
 */
public class GenericArrayWithTypeToken<T> {

    private T[] array;

    public GenericArrayWithTypeToken(Class<T> type, int size){
        array = (T[]) Array.newInstance(type,size);
    }

    public void put(int index, T item){
        array[index] = item;
    }

    public T get(int index) {
        return array[index];
    }

    public T[] rep() {
        return array;
    }

    // Expose the underlying representation

    public static void main(String[] args) {
        GenericArrayWithTypeToken<Integer> gai = new GenericArrayWithTypeToken<>(Integer.class,10);
        Integer[] ia = gai.rep();
    }
}
