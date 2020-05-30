package wxj.me.javase.generics.demostration;

/**
 * Copyright (C), 2015-2019, suning
 * FileName: GenericArray2
 * Author: wangxuejiao
 * Date: 2019/12/2 19:53
 * Description:
 * Version: 1.0.0
 */
public class GenericArray2<T> {

    private Object[] array;

    public GenericArray2(int size) {
        array = new Object[size];
    }

    public void put(int index, T item){
        array[index] = item;
    }

    @SuppressWarnings("unchecked")
    public T get(int index){
        return (T) array[index];
    }

    @SuppressWarnings("unchecked")
    public T[] rep() {
        return (T[]) array;
    }


  public static void main(String[] args) {
      GenericArray2<Integer> ss= new GenericArray2<>(11);
    }
}
