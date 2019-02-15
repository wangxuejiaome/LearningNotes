package wxj.me.javase.concurrency.exercise;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class SleepTask implements Runnable {

    private static int count;
    private final int id = ++count;
    private static Random random = new Random();
    private final int sleepTime = random.nextInt(10) + 1;

    @Override
    public void run() {

        try {
            TimeUnit.MILLISECONDS.sleep(sleepTime * 1000);
        } catch (InterruptedException e) {
            System.out.println(e);
        }
        System.out.println("#" + id + " sleep " + sleepTime + " second");
    }
}


public class E6SleepingTask {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < 5; i++) {
            executorService.execute(new SleepTask());
        }
        executorService.shutdown();
    }
}
