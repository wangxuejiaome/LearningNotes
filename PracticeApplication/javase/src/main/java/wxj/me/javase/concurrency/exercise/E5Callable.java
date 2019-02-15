package wxj.me.javase.concurrency.exercise;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

class FibonacciSeqCallable implements Callable<Integer> {

    public static int count;
    public final int id = ++count;

    public int n;

    public FibonacciSeqCallable(int n) {
        this.n = n;
    }

    public int fibonacciSeq(int n) {
        if (n < 2) {
            return n;
        } else {
            return fibonacciSeq(n - 1) + fibonacciSeq(n - 2);
        }
    }

    @Override
    public Integer call() {
        return fibonacciSeq(n);
    }
}

public class E5Callable {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        List<Future<Integer>> futures = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            futures.add(executorService.submit(new FibonacciSeqCallable(i)));
        }
        for (Future<Integer> future : futures){
            try {
                System.out.println(future.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } finally {
                executorService.shutdown();
            }
        }
    }
}
