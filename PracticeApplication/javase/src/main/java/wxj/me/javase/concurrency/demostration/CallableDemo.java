package wxj.me.javase.concurrency.demostration;


import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

class TaskWithResult implements Callable<String> {

    private int id;

    public TaskWithResult(int id) {
        this.id = id;
    }

    @Override
    public String call() {
        System.out.println(Thread.currentThread());
        return "result of TaskWithResult " + id;
    }
}

public class CallableDemo {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        ArrayList<Future<String>> results = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            results.add(executorService.submit(new TaskWithResult(i)));
        }
        for (Future<String> future : results){
            try {
                System.out.println(future.get());
            } catch (InterruptedException e) {
                System.out.println(e);
                return;
            } catch (ExecutionException e) {
                System.out.println(e);
            }finally {
                executorService.shutdown();
            }
        }
    }
}
