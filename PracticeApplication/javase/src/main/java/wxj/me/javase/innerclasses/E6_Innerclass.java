package wxj.me.javase.innerclasses;

import wxj.me.javase.innerclasses.exercise6.E6_Interface;
import wxj.me.javase.innerclasses.exercise6b.E6_SimpleClass;

public class E6_Innerclass extends E6_SimpleClass {

    public E6_Interface innerImpl(){
        E6_SimpleClass e6_simple = new E6_SimpleClass();
        return e6_simple.new InnerImpl();
    }

    public static void main(String[] args) {
        new E6_Innerclass().innerImpl().f();
    }
}
