package wxj.me.javase.generics.demostration;

/**
 * Copyright (C), 2015-2019, suning
 * FileName: Holder
 * Author: wangxuejiao
 * Date: 2019/12/19 10:04
 * Description:
 * Version: 1.0.0
 */
public class Holder<T> {

    private T value;
    public Holder() {}
    public Holder(T value) {
        this.value = value;
    }
    public void set(T value) {
        this.value = value;
    }
    public T get()  {
        return value;
    }
    public boolean equals(Object object){
        return value.equals(object);
    }

    public static void main(String[] args) {
        Holder<Apple> apple = new Holder<>(new Apple());
        Apple d = apple.get();
        apple.set(d);
        // 不可以向上转型，这种直接向上转型的语法不适用于泛型，
        // 但是通过给泛型加边界，编译器则认为可以向上转型
        // Holder<Fruit> fruit = apple; Cannot upcast
        Holder<? extends Fruit> fruit = apple; // OK
        // 给泛型加上边界以后，由于擦除到边界，这里编译器通过 get 获得的，它只知道得到的类型是 Fruit
        Fruit p = fruit.get();
        // 但是我们编写者，知道传入的值是 Apple 类型，我们可以进行强转，
        // 编译器是允许我们这样进行强转的，它知道这里存储的是 Fruit 的子类
        d = (Apple) fruit.get(); // Returns 'Object'
        System.out.println("d:" + d);
        try {
            // 虽然编译不会报错，但是实际类型如果装的是 Apple 类，
            // 你要转成 Orange 类，就会在运行时报类型转换错误
            Orange o = (Orange) fruit.get(); // No warning
        } catch (Exception e) {
            System.out.println(e);
        }
        // 这里泛型是 ? extends Fruit,这意味着它可以是任何继承在 Fruit 的类
        // 如果这里允许设置的话，编译器后面将无法保证类型转换安全性
        // 所以编译器就从源头上控制了不允许设置
        // fruit.set(new Apple()); // Cannot call set()
        // fruit.set(new Fruit()); // Cannot call set()
        System.out.println("p:" + p);
        System.out.println("fruit:" + fruit);
        System.out.println(fruit.equals(p)); // OK
    }
}
