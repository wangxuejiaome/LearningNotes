package wxj.me.javase.initialization;

import java.util.Arrays;

class Person{

    public Person(String name){
        System.out.println("Person constructor: name=" + name);
    }
}

public class E17_Array {

    public static void main(String[] args) {
        Person[] persons = {new Person("person1"),new Person("person2"),new Person("person3")};
    }

}
