package wxj.me.javase.concurrency.demostration;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static wxj.me.javase.util.Print.print;

class BlockedMutex {
    private Lock lock = new ReentrantLock();

    public BlockedMutex() {
        // Acquire it right way, to demonstrate interruption
        // of a task blocked on a ReentrantLock:
        lock.lock();
    }

    public void f() {
        try {
            // This will never be available to a second task
            lock.lockInterruptibly(); // Special call
            print("lock acquire in f()");
        }catch (InterruptedException e) {
            print("Interrrupted from lock acquisition in f()");
        }
    }
}

class Blocked2 implements Runnable {

    BlockedMutex blocked = new BlockedMutex();

    @Override
    public void run() {
        print("Waiting for f() in BlockedMutex");
        blocked.f();
        print("Broken out of blocked call");
    }
}

public class Interrupting2 {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new Blocked2());
        thread.start();
        TimeUnit.SECONDS.sleep(3);
        System.out.println("Issuing t.interrupt()");
        thread.interrupt();
    }
}

// new Blocked2() 是在主线程创建了一个 Blocked2 对象，因为在 Blocked2 的构造函数中，Blocked 对象已经获取了自己的锁
// 此时在创建新线程，将 blocked2 传入线程中，并且调用 f() 方法时，是处于阻塞状态的。
// 因为这个锁已经在主线中被 blocked2 对象获取了。
// 但是 ReentrantLock 乐观锁具有被中断的能力