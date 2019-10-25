package wxj.me.javase.concurrency.demostration;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import wxj.me.javase.util.BasicGenerator;
import wxj.me.javase.util.Generator;

/**
 * Create by 18113881 on 2019/5/27 16 : 50
 */

// Exchanger 是在两个任务之间交换对象的栅栏。当这些任务进入栅栏是，它们各自拥有一个对象，
    // 当它们离开时，它们拥有之前由对象持有的对象。Exchanger 的典型应用场景是：一个任务在创建对象，
    // 这些对象的生产代价高昂，而另一个任务在消费这些对象。同过这种方式，可以有更多的对象在创建的同时被消费。


class ExchangerProducer<T> implements Runnable {

    private Generator<T> generator;
    private Exchanger<List<T>> exchanger;
    private List<T> holder;

    ExchangerProducer(Exchanger<List<T>> exchanger,Generator<T> generator,List<T> holder) {
        this.exchanger = exchanger;
        this.generator = generator;
        this.holder = holder;
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                for (int i = 0; i < ExchangerDemo.size; i++) {
                    holder.add(generator.next());
                    // Exchanger full for empty:
                    holder = exchanger.exchange(holder);
                }
            }
        } catch (InterruptedException e) {
            // Ok to terminate this way
        }
    }
}

class ExchangerConsumer<T> implements Runnable {
    private Exchanger<List<T>> exchanger;
    private List<T> holder;
    private volatile T value;

    ExchangerConsumer(Exchanger<List<T>> exchanger, List<T> holder){
        this.exchanger = exchanger;
        this.holder = holder;
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                holder = exchanger.exchange(holder);
                for (T x : holder) {
                    value = x; // Fetch out value
                    holder.remove(x); // Ok for CopyOnWriteArrayList
                }
            }
        } catch (InterruptedException e) {
            // Ok to terminate this way.
        }
        System.out.println("Final value: " + value);
    }
}

public class ExchangerDemo {
    static int size = 10;
    static int delay = 5; // Seconds

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        Exchanger<List<Fat>> exchanger = new Exchanger<>();
        List<Fat> producerList = new CopyOnWriteArrayList<>();
        List<Fat> consumerList = new CopyOnWriteArrayList<>();
        executorService.execute(new ExchangerProducer<>(exchanger, BasicGenerator.create(Fat.class),producerList));
        executorService.execute(new ExchangerConsumer<>(exchanger,consumerList));
        TimeUnit.SECONDS.sleep(delay);
        executorService.shutdownNow();
    }
}
