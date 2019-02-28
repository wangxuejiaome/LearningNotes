package wxj.me.javase.concurrency.demostration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NativeExceptionHandling {

    public static void main(String[] args){
        try {
            ExecutorService executorService = Executors.newCachedThreadPool();
            executorService.execute(new ExceptionThread());
        }catch (RuntimeException e){
            // This statement will NOT execute!
            System.out.println("Exception has been handled!");
        }
    }
}
