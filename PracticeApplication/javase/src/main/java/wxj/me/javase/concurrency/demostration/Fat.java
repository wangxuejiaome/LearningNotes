package wxj.me.javase.concurrency.demostration;

/**
 * Create by 18113881 on 2019/5/23 17 : 42
 */
public class Fat {
    private volatile double d; // Prevent optimization
    private static int counter = 0;
    private final int id = counter++;

    public Fat() {
        // Expensive interruptible operation:
        for (int i = 0; i < 10000; i++) {
            d += (Math.PI + Math.E) / (double) i;
        }
    }

    public void operation() {
        System.out.println(this);
    }

    public String toString() {
        return "Fat id:" + id;
    }
}
