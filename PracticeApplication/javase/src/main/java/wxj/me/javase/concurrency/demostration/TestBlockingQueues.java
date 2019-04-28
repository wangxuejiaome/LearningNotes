package wxj.me.javase.concurrency.demostration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.SynchronousQueue;

import static wxj.me.javase.util.Print.print;

/**
 * Create by 18113881 on 2019/4/25 17 : 23
 */

class LiftOffRunner implements Runnable {
    private BlockingQueue<LiftOff> rockets;

    public LiftOffRunner(BlockingQueue<LiftOff> queue) {
        rockets = queue;
    }

    public void add(LiftOff liftOff) {
        try {
            rockets.put(liftOff);
            System.out.println("LiftOfferRunner add on Thread: " + Thread.currentThread().getName() );
        } catch (InterruptedException e) {
            print("Interrupted during put()");
        }
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                System.out.println("LiftOfferRunner run");
                LiftOff rocket = rockets.take();
                rocket.run();
            }
        } catch (InterruptedException e) {
            print("Waking from take()");
        }
        print("Exiting LiftOffRunner");
    }
}

public class TestBlockingQueues {
    static void getKey() {
        try {
            // Compensate for Windows/Linux difference in the
            // length of the result produce by the Enter key:
            new BufferedReader(new InputStreamReader(System.in)).readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static void getKey(String message) {
        print(message);
        getKey();
    }

    static void test(String msg, BlockingQueue<LiftOff> queue) {
        print(msg);
        LiftOffRunner runner = new LiftOffRunner(queue);
        Thread t = new Thread(runner);
        t.start();
        for (int i = 0; i < 5; i++) {
            runner.add(new LiftOff(5));
        }
        getKey("Press 'Enter' (" + msg + ")");
        t.interrupt();
        print("Finished " + msg + "test");
    }

    public static void main(String[] args) {
        // Unlimited size
        test("LinkedBlockingQueue",new LinkedBlockingDeque<LiftOff>());
        // Fixed size
        test("ArrayBlockingQueue",new ArrayBlockingQueue<LiftOff>(3));
        // Size of 1
        test("SynchronizedQueue",new SynchronousQueue<LiftOff>());
    }
}
