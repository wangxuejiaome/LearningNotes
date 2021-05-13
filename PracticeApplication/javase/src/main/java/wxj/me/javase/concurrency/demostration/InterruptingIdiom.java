package wxj.me.javase.concurrency.demostration;

import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

import static wxj.me.javase.util.Print.print;

class NeedsCleanup {
    private final int id;

    public NeedsCleanup(int ident) {
        id = ident;
        print("NeedsCleanup " + id);
    }

    public void cleanup() {
        print("Cleaning up " + id);
    }
}

class Blocked3 implements Runnable {
    private volatile double d = 0.0;

    public void run() {
        try {
            while (!Thread.interrupted()) {
                // point1
                NeedsCleanup needsCleanup1 = new NeedsCleanup(1);
                // Start try-finally immediately after definition
                // of needsCleanup1, to guarantee proper cleanup of needsCleanup1:
                try {
                    print("Sleeping");
                    TimeUnit.SECONDS.sleep(1);
                    // point2
                    NeedsCleanup needsCleanup2 = new NeedsCleanup(2);
                    // Guarantee proper cleanup of needsCleanup2:
                    try {
                        print("Calculating");
                        // A time-consuming, non-blocking operation:
                        for (int i = 0; i < 2500000; i++) {
                            d = d + (Math.PI + Math.E) / d;
                        }
                        print("Finished time-consuming operation");
                    } finally {
                        needsCleanup2.cleanup();
                    }
                } finally {
                    needsCleanup1.cleanup();
                }
            }
        } catch (InterruptedException e) {
            print("Exiting via InterruptedException");
        }
    }
}

public class InterruptingIdiom {
    public static void main(String[] args) throws Exception {
        /*if(args.length != 1) {
            print("usage: java InterruptingIdiom delay-in-mS");
            System.exit(1);
        }*/
        Thread thread = new Thread(new Blocked3());
        thread.start();
        TimeUnit.MILLISECONDS.sleep(/*new Integer(args[0])*/ 1100);
        thread.interrupt();
    }
}
