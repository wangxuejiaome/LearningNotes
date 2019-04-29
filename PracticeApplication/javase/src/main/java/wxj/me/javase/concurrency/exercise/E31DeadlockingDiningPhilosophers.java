package wxj.me.javase.concurrency.exercise;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import static wxj.me.javase.util.Print.print;

class Chopstick2 {
    private final int id;

    public Chopstick2(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Chopstick " + id;
    }
}

class ChopsticksHolder extends LinkedBlockingQueue<Chopstick2> {

    BlockingQueue<Chopstick2> blockingQueue;
    Chopstick2 right;
    Chopstick2 left;

    public ChopsticksHolder(BlockingQueue<Chopstick2> blockingQueue) {
        this.blockingQueue = blockingQueue;
    }

    public synchronized void takeChopsticks() throws InterruptedException {
        right = blockingQueue.take();
        print("grabbing right " + right);
        left = blockingQueue.take();
        print("grabbing left " + left);
    }

    public synchronized void dropChopsticks() throws InterruptedException {
        blockingQueue.put(right);
        print("drop right " + right);
        blockingQueue.put(left);
        print("drop left " + left);
    }

}

class Philosopher implements Runnable {
    private ChopsticksHolder chopsticksHolder;
    private final int id;
    private final int ponderFactor;
    private Random random = new Random(47);

    public Philosopher(ChopsticksHolder chopsticksHolder, int id, int ponderFactor) {
        this.chopsticksHolder = chopsticksHolder;
        this.id = id;
        this.ponderFactor = ponderFactor;
    }

    private void pause() throws InterruptedException {
        if (ponderFactor == 0) {
            return;
        }
        TimeUnit.MILLISECONDS.sleep(random.nextInt(ponderFactor * 250));
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                print(this + " " + "thinking");
                pause();
                // Philosopher becomes hungry
                print(this + " " + "hungry");
                chopsticksHolder.takeChopsticks();
                pause();
                print(this + " " + "eating end");
                chopsticksHolder.dropChopsticks();
            }
        } catch (InterruptedException e) {
            print(this + " " + "exiting via interrupt");
        }
    }

    public String toString() {
        return "Philosopher " + id;
    }
}

public class E31DeadlockingDiningPhilosophers {

    public static void main(String[] args) throws InterruptedException, IOException {
        int ponder = 0;
        int size = 5;
        ExecutorService executorService = Executors.newCachedThreadPool();
        BlockingQueue<Chopstick2> blockingQueue = new ArrayBlockingQueue<>(size);
        for (int i = 0; i < size; i++) {
            blockingQueue.put(new Chopstick2(i));
        }
        ChopsticksHolder chopsticksHolder = new ChopsticksHolder(blockingQueue);
        for (int i = 0; i < size; i++) {
            executorService.execute(new Philosopher(chopsticksHolder, i, ponder));
        }

        System.out.println("Press 'Enter' to quit");
        System.in.read();
        executorService.shutdownNow();
    }
}
