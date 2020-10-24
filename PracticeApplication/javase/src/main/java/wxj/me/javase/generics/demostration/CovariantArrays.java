package wxj.me.javase.generics.demostration;

/**
 * Copyright (C), 2015-2019, suning
 * FileName: CovariantArrays
 * Author: wangxuejiao
 * Date: 2019/12/17 20:33
 * Description:
 * Version: 1.0.0
 */

class Fruit {}
class Apple extends Fruit{}
class Jonathan extends Apple {}
class Orange extends Fruit{}

public class CovariantArrays {
    public static void main(String[] args) {
        Fruit[] fruits = new Apple[10];
        fruits[0] = new Apple();// OK
        fruits[1] = new Jonathan(); // OK
        // Runtime type is Apple[], not Fruit[] or Orange[]
        try {
            // Compiler allows you to add Fruit:
            fruits[0] = new Fruit(); // ArrayStoreException
        } catch (Exception e) {
            System.out.println(e);
        }
        try {
            // Compiler allows you to add Oranges:
            fruits[0] = new Orange(); // ArrayStoreException
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
/**
 *  Fruit[] fruits = new Apple[10]; 创建了实际类型是 Apple 类型的数组，
 *  将 Fruit 实例类赋值给 fruits 数组，虽然编译时不出错，
 *  但是在运行时，会发现 Fruit、Orange 类型不是 Apple 会报数组存储异常错误。
 *
 * **/