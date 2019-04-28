package wxj.me.javase.concurrency.exercise;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Create by 18113881 on 2019/4/25 10 : 51
 */

class FlowQueue<T> {
    private Queue<T> queue = new LinkedList<>();
    private int maxSize;

    public FlowQueue(int maxSize) {
        this.maxSize = maxSize;
    }

    public synchronized void put(T v) throws InterruptedException {
        while (queue.size() >= maxSize) {
            wait();
        }
        queue.offer(v);
        maxSize++;
        notifyAll();
    }

    public synchronized T get() throws InterruptedException {
        while (queue.isEmpty()) {
            wait();
        }
        T returnVale = queue.poll();
        maxSize--;
        notifyAll();
        return returnVale;
    }
}

class Item {
    private static int counter;
    private int id = counter++;

    public String toString() {
        return "Item " + id;
    }
}

// Produces Item objects
class Producer implements Runnable {
    private int delay;
    private FlowQueue<Item> output;

    public Producer(FlowQueue<Item> output, int sleepTime) {
        this.output = output;
        delay = sleepTime;
    }

    @Override
    public void run() {
        for (; ; ) {
            try {
                output.put(new Item());
                TimeUnit.MILLISECONDS.sleep(delay);
            } catch (InterruptedException e) {
                return;
            }
        }
    }
}

class Consumer implements Runnable {
    private int delay;
    private FlowQueue<?> input;

    public Consumer(FlowQueue<?> input, int sleepTime) {
        this.input = input;
        delay = sleepTime;
    }

    @Override
    public void run() {
        for (; ; ) {
            try {
                System.out.println(input.get());
                TimeUnit.MILLISECONDS.sleep(delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

public class E24_Answer {
    public static void main(String[] args) throws InterruptedException {
        FlowQueue<Item> flowQueue = new FlowQueue<>(100);
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.execute(new Producer(flowQueue, 200));
        executorService.execute(new Consumer(flowQueue, 1));
        TimeUnit.SECONDS.sleep(2);
        executorService.shutdownNow();
    }
}


// init maxSize: 5
// put() 执行过程：
// offer    maxSize++ : 6   notifyAll
