package wxj.me.javase.concurrency.demostration;

public class EvenGenerator extends IntGenerator {

    private  int currentEvenValue = 0;

    @Override
    public int next() {
        // Danger point here!
        ++currentEvenValue;
        ++currentEvenValue;
        return currentEvenValue;
    }

    public static void main(String[] args) {
        EvenChecker.test(new EvenGenerator());
    }
}
