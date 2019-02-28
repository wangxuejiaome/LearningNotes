package wxj.me.javase.concurrency.demostration;

import java.util.concurrent.TimeUnit;

/**
 * 由后台线程创建出的线程，都是后台线程
 */
class Daemon implements Runnable {

    private Thread[] t = new Thread[10];

    @Override
    public void run() {
        for (int i = 0; i < t.length; i++) {
            t[i] = new Thread(new DaemonSpawn());
            t[i].start();
            System.out.println("DaemonSpawn " + i + " started.");
        }
        for (int i = 0; i < t.length; i++) {
            System.out.println("t[" + i + "].isDemon() = " + t[i].isDaemon() + ". ");
        }
        while (true){
            Thread.yield();
        }
    }
}

class DaemonSpawn implements Runnable{

    @Override
    public void run() {
        while (true){
            Thread.yield();
        }
    }
}

public class Daemons {

    public static void main(String[] args) throws InterruptedException {
        Thread d = new Thread(new Daemon());
        d.setDaemon(true);
        d.start();
        System.out.println("d.isDaemon() = " + d.isDaemon() + ", ");
        // Allow the daemon threads to finish their starup process:
        TimeUnit.MILLISECONDS.sleep(100);
    }
}
