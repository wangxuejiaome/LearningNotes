package wxj.me.javase.concurrency.demostration;

/**
 * Create by 18113881 on 2019/4/28 11 : 08
 */
public class Chopstick {
    private boolean taken = false;

    public synchronized void take() throws InterruptedException {
        while (taken) {
            wait();
        }
        taken = true;
    }

    public synchronized void drop() {
        taken = false;
        notifyAll();
    }

}
