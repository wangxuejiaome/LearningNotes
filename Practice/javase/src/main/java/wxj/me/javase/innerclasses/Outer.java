package wxj.me.javase.innerclasses;

/**
 * Write a class named Outer containing an
 * inner class named Inner. Add a method to Outer
 * that returns an object of type Inner. In
 * main(), create and initialize a reference to
 * an Inner.
 */
public class Outer {

    public Inner getInner(){
        return new Inner();
    }

    class Inner{
        Inner(){
            System.out.println("Inner Constructor");
        }
    }

    public static void main(String[] args) {
        Outer outer = new Outer();
        Outer.Inner inner = outer.getInner();
    }
}
