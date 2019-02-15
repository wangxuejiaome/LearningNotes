package wxj.me.javase.concurrency.demostration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *  使用线程池可以一次性预先执行高昂的线程分配，
 *  在事件驱动的系统中，需要线程的事件处理器，通过直接从线程池中获取线程，可以快速的响应事件处理。
 *  在任何线程池中，现有线程在可能的情况下，都会被自动复用。
 */
public class CachedThreadPool {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < 5; i++) {
            executorService.execute(new LiftOff());
        }
        executorService.shutdown();
    }
}
