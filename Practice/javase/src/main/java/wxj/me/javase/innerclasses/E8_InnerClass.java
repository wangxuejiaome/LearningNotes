package wxj.me.javase.innerclasses;

/**
 * Determine whether an outer class has access to
 * the private elements of its inner class.
 */
class Outer8{

    class Inner{
        private String value = "inner private field value";

        private void f(){
            System.out.println("inner private f()");
        }
    }

    public void h(){
        Inner inner = new Inner();
        System.out.println(inner.value);
        inner.f();
    }
}

public class E8_InnerClass {

    public static void main(String[] args) {
        Outer8 outer8 = new Outer8();
        outer8.h();
    }
}

// 外部类同样可以访问内部类的私有成员··