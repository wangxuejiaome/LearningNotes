package wxj.me.javase.innerclasses;

/**
 * Create a private inner class that implements a
 * public interface. Write a method that returns
 * a reference to an instance of the private
 * inner class, upcast to the interface. Show
 * that the inner class is completely hidden by
 * trying to downcast to it.
 */
interface E11_Interface{
    void f();
}

class Outer11{

    private class Inner implements E11_Interface{

        @Override
        public void f() {
            System.out.println("Inner f()");
        }
    }

    public E11_Interface inner(){
        return new Inner();
    }

}



public class E11_InnerClass {

    public static void main(String[] args) {
        Outer11 outer11 = new Outer11();
    E11_Interface inner = outer11.inner();
}
}

// 对应私有的内部类，只有外部类自生可以访问，其他类都无法访问。只能将内部类向上转为它实现的公开接口
