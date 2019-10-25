package wxj.me.javase.generics.exersice;

/**
 * Copyright (C), 2015-2019, suning
 * FileName: E2Generic
 * Author: wangxuejiao
 * Date: 2019/10/17 15:09
 * Description:
 * Version: 1.0.0
 */
public class E2Generic<T> {

    private T object1;
    private T object2;
    private T object3;

    public E2Generic(T object1, T object2, T object3) {
        this.object1 = object1;
        this.object2 = object2;
        this.object3 = object3;
    }

    public T getObject1() {
        return object1;
    }

    public void setObject1(T object1) {
        this.object1 = object1;
    }

    public T getObject2() {
        return object2;
    }

    public void setObject2(T object2) {
        this.object2 = object2;
    }

    public T getObject3() {
        return object3;
    }

    public void setObject3(T object3) {
        this.object3 = object3;
    }

    public static void main(String[] args) {
        Person person1 = new Person("person1",12);
        Person person2 = new Person("person2",13);
        Person person3 = new Person("person3",14);
        E2Generic<Person> genericHolder = new E2Generic<>(person1,person2,person3);

        System.out.println(genericHolder.object1.getName());
        System.out.println(genericHolder.object2.getName());
        System.out.println(genericHolder.object3.getName());
    }
}

class Person {
    private String name;
    private int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}