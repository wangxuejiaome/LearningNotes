package wxj.me.javase.innerclasses;

/**
 * Create a class with a private field and a
 * private method. Create an inner class with a
 * method that modifies the outer-class field and
 * calls the outer-class method. In a second
 * outer-class method, create an object of the
 * inner class and call its method, then show
 * the effect on the outer-class object.
 */

class Outer7{

    private String value = "init value";

    @Override
    public String toString() {
        return value;
    }

    class Inner{

        public void f(){
            value = "Inner update private value";
            System.out.println(Outer7.this.toString());
        }
    }

    public void outerF(){
        Inner inner = new Inner();
        inner.f();
    }
}

public class E7_InnerClass {

    public static void main(String[] args) {
        Outer7 outer7 = new Outer7();
        outer7.outerF();
    }
}

// 内部类可以访问外部类的所有成员，包括私有成员