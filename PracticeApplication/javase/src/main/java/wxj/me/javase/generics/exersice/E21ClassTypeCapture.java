package wxj.me.javase.generics.exersice;

import java.util.HashMap;
import java.util.Map;

/**
 * Copyright (C), 2015-2019, suning
 * FileName: E21ClassTypeCapture
 * Author: wangxuejiao
 * Date: 2019/11/28 16:43
 * Description:
 * Version: 1.0.0
 */

class Building {}
class House extends Building {}

public class E21ClassTypeCapture<T> {
    Class<T> kind;
    Map<String,Class<?>> classMap = new HashMap<>();
    public E21ClassTypeCapture(Class<T> kind){
        this.kind = kind;
    }
    public boolean f(Object arg) {
        return kind.isInstance(arg);
    }

    public void addType(String typename,Class<?> kind){
        classMap.put(typename,kind);
    }

    public T createNew(String typeName) throws IllegalAccessException, InstantiationException {
        return (T) classMap.get(typeName).newInstance();
    }

    public static void main(String[] args){
        E21ClassTypeCapture<Building> ctp1 = new E21ClassTypeCapture<>(Building.class);
        System.out.println(ctp1.f(new Building()));
        System.out.println(ctp1.f(new House()));

        E21ClassTypeCapture<House> ctp2 = new E21ClassTypeCapture<>(House.class);
        System.out.println(ctp2.f(new Building()));
        System.out.println(ctp2.f(new House()));
    }

}
