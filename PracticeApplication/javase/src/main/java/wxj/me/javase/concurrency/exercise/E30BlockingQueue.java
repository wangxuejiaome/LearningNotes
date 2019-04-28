package wxj.me.javase.concurrency.exercise;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

import static wxj.me.javase.util.Print.print;
import static wxj.me.javase.util.Print.printnb;

/**
 * Create by 18113881 on 2019/4/28 09 : 56
 */

class Sender implements Runnable {
    private Random random = new Random(47);
    private BlockingQueue<Character> blockingQueue = new LinkedBlockingDeque<>();

    public BlockingQueue<Character> getBlockingQueue() {
        return blockingQueue;
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                for (char c = 'A'; c <= 'z'; c++) {
                    blockingQueue.put(c);
                    TimeUnit.MILLISECONDS.sleep(random.nextInt(500));
                }
            }
        } catch (InterruptedException e) {
            print(e + " Sender interrupted");
        }
    }
}

class Receiver implements Runnable {
    BlockingQueue<Character> blockingDeque;

    public Receiver(BlockingQueue<Character> sender) {
        blockingDeque = sender;
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                // Blocks until characters are there:
                printnb("Read: " + blockingDeque.take() + ". ");
            }
        } catch (InterruptedException e) {
            print(e + " Receiver interrupted");
        }
    }
}

public class E30BlockingQueue {
    public static void main(String[] args) throws Exception {
        Sender sender = new Sender();
        Receiver receiver = new Receiver(sender.getBlockingQueue());
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(sender);
        executorService.execute(receiver);
        TimeUnit.SECONDS.sleep(4);
        executorService.shutdownNow();
    }
}
