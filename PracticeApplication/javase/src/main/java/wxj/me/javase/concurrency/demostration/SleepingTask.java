package wxj.me.javase.concurrency.demostration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SleepingTask extends LiftOff {

    @Override
    public void run() {
        try {
            while (countDown-- > 0) {
                System.out.println(status());

                TimeUnit.MILLISECONDS.sleep(100);

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < 5; i++) {
            executorService.execute(new SleepingTask());
        }
        executorService.shutdown();
    }
}
