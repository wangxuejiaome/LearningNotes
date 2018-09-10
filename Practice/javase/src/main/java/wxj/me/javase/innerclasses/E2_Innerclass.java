package wxj.me.javase.innerclasses;

/**
 * Create a class that holds a String, with a
 * toString() method that displays this String.
 * Add several instances of your new class to a
 * Sequence object, then display them.
 */

class StringHolder{

    private String value;

    public StringHolder(String value){
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}

public class E2_Innerclass {

    public static void main(String[] args) {
        Sequence sequence = new Sequence(10);
        for (int i = 0; i < 10; i++) {
            sequence.add(new StringHolder(Integer.toString(i)));
        }
        Selector selector = sequence.selector();
        while (!selector.end()){
            System.out.print(selector.current());
            selector.next();
        }
    }
}
