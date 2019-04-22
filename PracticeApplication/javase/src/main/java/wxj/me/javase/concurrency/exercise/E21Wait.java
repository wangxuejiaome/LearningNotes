package wxj.me.javase.concurrency.exercise;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Create by 18113881 on 2019/4/22 16 : 16
 */

class Task1 implements Runnable {

    @Override
    public void run() {
        synchronized (this) {
            try {
                System.out.println("Task1 wait!");
                wait();
                System.out.println("Task1 run end!");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class Task2 implements Runnable {

    Runnable otherTask;

    public Task2(Runnable otherTask) {
        this.otherTask = otherTask;
    }

    @Override
    public void run() {
        try {
            System.out.println("Task2 run");
            TimeUnit.SECONDS.sleep(2);
            synchronized (otherTask) {
                otherTask.notify();
            }
        } catch (InterruptedException e) {
            System.out.println("End by interrupted");
        }
    }
}

public class E21Wait {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        Task1 task1 = new Task1();
        executorService.execute(task1);
        executorService.execute(new Task2(task1));
        Thread.yield();
        executorService.shutdown();
    }
}
