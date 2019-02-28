package wxj.me.javase.concurrency.demostration;

import java.util.concurrent.TimeUnit;

/**
 *  后台线程（daemon）：在程序运行的时候在后台提供一种通用服务的线程。
 *  特点：当所有非后台线程结束时，会同时杀死所有后台线程，程序就终止了。
 */

public class SimpleDaemons implements Runnable {

    @Override
    public void run() {
        try {
            while (true) {
                TimeUnit.MILLISECONDS.sleep(100);
                System.out.println(Thread.currentThread() + " " + this);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        for (int i = 0; i < 10; i++) {
            Thread daemonThread = new Thread(new SimpleDaemons());
            daemonThread.setDaemon(true);
            daemonThread.start();
        }
        TimeUnit.MILLISECONDS.sleep(175);
    }
}
