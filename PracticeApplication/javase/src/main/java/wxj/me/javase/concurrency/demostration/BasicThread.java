package wxj.me.javase.concurrency.demostration;

/**
 * 要将 Runnable 对象运行起来，经典的方式就是把它交给一个 Thread 构造器。
 */
public class BasicThread {

    public static void main(String[] args) {
        Thread thread = new Thread(new LiftOff());
        thread.start();
        System.out.println("Waiting for LiftOff");
    }
}
