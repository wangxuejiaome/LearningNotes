package wxj.me.javase.concurrency.exercise;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Create by 18113881 on 2019/4/22 19 : 16
 */
public class E22AnswerWay2 {

    public static void main(String[] args) throws InterruptedException {
        final Runnable runnable1 = new Runnable() {
            @Override
            public void run() {
                for (; ; ) {
                    try {
                        TimeUnit.MILLISECONDS.sleep(100);
                        synchronized (this){
                            notify();
                        }
                    } catch (InterruptedException e) {
                        return;
                    }
                }
            }
        };

        Runnable runnable2 = new Runnable() {
            @Override
            public void run() {
                for (; ; ) {
                    try {
                        synchronized (runnable1) {
                            runnable1.wait();
                        }
                    } catch (InterruptedException e) {
                        return;
                    }
                    System.out.println("Cycled");
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
