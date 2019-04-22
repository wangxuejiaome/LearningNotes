package wxj.me.javase.concurrency.exercise;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Create by 18113881 on 2019/4/22 19 : 03
 */
public class E22Answer {
    private static volatile boolean flag;
    private static int spins;

    public static void main(String[] args) throws InterruptedException {
        Runnable runnable1 = new Runnable() {
            @Override
            public void run() {
                for(;;){
                    try {
                        TimeUnit.MILLISECONDS.sleep(10);
                    } catch (InterruptedException e) {
                        return;
                    }
                    flag = true;
                }
            }
        };

        Runnable runnable2 = new Runnable() {
            @Override
            public void run() {
                for(;;){
                    while (!flag && !Thread.currentThread().isInterrupted()){
                        spins++;
                    }
                    System.out.println("Spun " + spins + " times");
                    spins = 0;
                    flag = false;
                    if(Thread.interrupted()){
                        return;
                    }
                }
            }
        };

        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(runnable1);
        executorService.execute(runnable2);
        TimeUnit.SECONDS.sleep(1);
        executorService.shutdownNow();
    }
}
