package wxj.me.javase.concurrency.demostration;

/**
 * 1.从输出结果看，不同任务的执行交错进行着。
 * 这种交错是由线程调度器自动控制的，
 * 并且是非确定的，每次运行的结果都可能不同。
 *
 * 2.一个线程会创建一个单独的执行线程，在对 start() 的调用完成之后，它依旧会继续存在。
 */
public class MoreBasicThreads {

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            new Thread(new LiftOff()).start();
        }
        System.out.println("Waiting for LiftOff");
    }
}
