package wxj.me.javase.innerclasses;

/**
 * Create a class with an inner class. In a
 * separate class, make an instance of the inner
 * class.
 */
class Outer5{

    class Inner5{
        {
            System.out.println("Inner5 created");
        }
    }
}

public class E5_Innerclass {

    public static void main(String[] args) {
        Outer5 outer5 = new Outer5();
        outer5.new Inner5();
    }


}
