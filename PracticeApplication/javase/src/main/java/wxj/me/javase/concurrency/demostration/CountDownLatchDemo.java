package wxj.me.javase.concurrency.demostration;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static wxj.me.javase.util.Print.print;

/**
 * Create by 18113881 on 2019/4/29 20 : 58
 */

/*class TaskPortion implements Runnable {
    private static int counter = 0;
    private final int id = counter++;
    private static Random rand = new Random(47);
    private final CountDownLatch latch;

    TaskPortion(CountDownLatch latch) {
        this.latch = latch;
    }

    public void run() {
        try {
            doWork();
            latch.countDown();
        } catch (InterruptedException ex) {
// Acceptable way to exit
        }
    }

    public void doWork() throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(rand.nextInt(2000));
        print(this + "completed");
    }

    public String toString() {
        return String.format("%1$-3d ", id);
    }
}

// Waits on the CountDownLatch:
class WaitingTask implements Runnable {
    private static int counter = 0;
    private final int id = counter++;
    private final CountDownLatch latch;

    WaitingTask(CountDownLatch latch) {
        this.latch = latch;
    }

    public void run() {
        try {
            latch.await();
            print("Latch barrier passed for " + this);
        } catch (InterruptedException ex) {
            print(this + " interrupted");
        }
    }

    public String toString() {
        return String.format("WaitingTask %1$-3d ", id);
    }
}

public class CountDownLatchDemo {
    static final int SIZE = 100;

    public static void main(String[] args) throws Exception {
        ExecutorService exec = Executors.newCachedThreadPool();
// All must share a single CountDownLatch object:
        CountDownLatch latch = new CountDownLatch(SIZE);
        for (int i = 0; i < 10; i++)
            exec.execute(new WaitingTask(latch));
        for (int i = 0; i < SIZE; i++)
            exec.execute(new TaskPortion(latch));
        print("Launched all tasks");
        exec.shutdown(); // Quit when all tasks complete
    }
}*/

class TaskPortion implements Runnable {
    private static int counter = 0;
    private final int id = counter++;
    private static Random random = new Random(47);
    private final CountDownLatch countDownLatch;

    TaskPortion(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        try {
            doWork();
            countDownLatch.countDown();
        } catch (InterruptedException e) {
            // Acceptable way to exit
        }
    }

    public void doWork() throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(random.nextInt(2000));
        print(this + "completed");
    }

    public String toString() {
        return String.format("%1$-3d ", id);
    }
}

class WaitingTask implements Runnable {
    private static int counter = 0;
    private final int id = counter++;
    private final CountDownLatch countDownLatch;

    WaitingTask(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        try {
            countDownLatch.await();
            print("Latch barrier passed for" + this);
        } catch (InterruptedException e) {
            print(this + " interrupted");
        }
    }

    @Override
    public String toString() {
        return String.format("WaitingTask %1$-3d ", id);
    }
}

public class CountDownLatchDemo {
    static final int SIZE = 100;

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        // All must share a single CountDownLatch object:
        CountDownLatch countDownLatch = new CountDownLatch(SIZE);
        for (int i = 0; i < 10; i++) {
            executorService.execute(new WaitingTask(countDownLatch));
        }
        for (int i = 0; i < SIZE; i++) {
            executorService.execute(new TaskPortion(countDownLatch));
        }
        print("Launched all tasks");
        executorService.shutdown();
    }
}
