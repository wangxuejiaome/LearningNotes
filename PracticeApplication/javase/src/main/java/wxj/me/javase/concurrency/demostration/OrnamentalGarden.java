package wxj.me.javase.concurrency.demostration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static wxj.me.javase.util.Print.print;

class Count {
    private int count = 0;
    private Random rand = new Random(47);

    // Remove the synchronized keyword to see counting fail:
    public synchronized int increment() {
        int temp = count;
        if (rand.nextBoolean()) {
            // Yield half the time
            Thread.yield();
        }
        return (count = ++temp);
    }

    public synchronized int value() {
        return count;
    }
}

class Entrance implements Runnable {

    private static Count count = new Count();
    private static List<Entrance> entrances = new ArrayList<>();
    private int number = 0;
    // Doesn't need synchronized to read:
    private final int id;
    private static volatile boolean canceled = false;

    public static void cancel() {
        canceled = true;
    }

    public Entrance(int id) {
        this.id = id;
        // Keep this task in a list. Also prevents garbage collection of dead tasks:
        entrances.add(this);
    }

    @Override
    public void run() {
        while (!canceled) {
            synchronized (this) {
                ++number;
            }
            print(this + " Total: " + count.increment());
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
                print("sleep interrupted");
            }
        }
        print("Stopping " + this);
    }

    public synchronized int getValue() {
        return number;
    }

    public String toString() {
        return "Entrance " + id + ": " + getValue();
    }

    public static int getTotalCount() {
        return count.value();
    }

    public static int sumEntrances() {
        int sum = 0;
        for (Entrance entrance : entrances) {
            sum += entrance.getValue();
        }
        return sum;
    }
}

public class OrnamentalGarden {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < 5; i++) {
            executorService.execute(new Entrance(i));
        }
        // Run for a while,then stop and collect the data;
        TimeUnit.SECONDS.sleep(3);
        Entrance.cancel();
        executorService.shutdown();
        if (!executorService.awaitTermination(250, TimeUnit.MILLISECONDS)) {
            print("Some task were not terminated()!");
        }
        print("Total: " + Entrance.getTotalCount());
        print("Sum of Entrances:" + Entrance.sumEntrances());
    }
}
