package wxj.me.javase.concurrency.demostration;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class AttemptLocking {

    private ReentrantLock lock = new ReentrantLock();

    public void unTimed() {
        boolean captured = lock.tryLock();
        try {
            System.out.println("tryLock(): " + captured);
        } finally {
            if (captured)
                lock.unlock();
        }
    }

    public void timed() {
        boolean captured = false;
        try {
            captured = lock.tryLock(2, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        try {
            System.out.println("tryLock(2,TimeUnit.SECONDS)" + captured);
        } finally {
            if (captured)
                lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        final AttemptLocking attemptLocking = new AttemptLocking();
        attemptLocking.unTimed(); // True -- lock is available
        attemptLocking.timed(); // True -- lock is available
        // Now create a separate task to grab the lock
        new Thread() {
            //{setDaemon(true);}
            public void run() {
                attemptLocking.lock.lock();
                System.out.println("acquired");
            }
        }.start();
        // Give the 2nd task a chance
        Thread.sleep(2000);
        //Thread.yield();
        attemptLocking.unTimed(); // False -- lock grabbed by task
        attemptLocking.timed(); // False -- lock grabbed by task
    }

}
