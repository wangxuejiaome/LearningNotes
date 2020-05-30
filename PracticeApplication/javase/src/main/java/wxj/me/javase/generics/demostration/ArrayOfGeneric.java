package wxj.me.javase.generics.demostration;

/**
 * Copyright (C), 2015-2019, suning
 * FileName: ArrayOfGeneric
 * Author: wangxuejiao
 * Date: 2019/12/2 17:25
 * Description:
 * Version: 1.0.0
 */
public class ArrayOfGeneric {
    static final int SIZE = 100;
    static Generic<Integer>[] gia;

    public static void main(String[] args) {
        // Compiles: produces ClassCastException  可以编译通过，但是运行会报类型转换异常
        //! gia = (Generic<Integer>[]) new Object[SIZE];
        // Runtime type is raw (erased) type
        gia = (Generic<Integer>[])new Generic[SIZE];
        System.out.println(gia.getClass().getSimpleName());
        gia[0] = new Generic<>();
        // Compile-time error
        //! gia[1] = new Object();
        // Discovers type mismatch at compile time.
        //! gia[2] = new Generic<Double>();
    }
}
