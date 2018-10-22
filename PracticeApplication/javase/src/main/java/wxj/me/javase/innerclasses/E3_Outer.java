package wxj.me.javase.innerclasses;

/**
 * Modify Exercise 1 so Outer has a private
 * String field (initialized by the constructor),
 * and Inner has a toString() that displays this
 * field. Create an object of type Inner and
 * display it.
 */
class Outer2{
    private String value;

    public Outer2(String value){
        this.value = value;
    }

    public Inner getInner(){
        return new Inner();
    }

    class Inner{
        Inner(){
            System.out.println("Inner Constructor");
        }

        @Override
        public String toString() {
            return value;
        }
    }
}

public class E3_Outer {

    public static void main(String[] args) {
        Outer2 outer2 = new Outer2("Inner accessing outer field value");
        Outer2.Inner inner = outer2.getInner();
        System.out.println(inner);
    }
}
