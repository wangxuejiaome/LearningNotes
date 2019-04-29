package wxj.me.javase.concurrency.demostration;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Create by 18113881 on 2019/4/28 14 : 00
 */
public class DeadlockingDiningPhilosophers {
    public static void main(String[] args) throws IOException {
        int ponder = 0;
        int size = 5;
        ExecutorService executorService = Executors.newCachedThreadPool();
        Chopstick[] chopsticks = new Chopstick[size];
        for (int i = 0; i < size; i++) {
            chopsticks[i] = new Chopstick();
        }
        for (int i = 0; i < size; i++) {
            executorService.execute(new Philosopher(chopsticks[i], chopsticks[(i + 1) % size], i, ponder));
        }
        System.out.println("Press 'Enter' to quit");
        System.in.read();
        executorService.shutdownNow();
    }
}

