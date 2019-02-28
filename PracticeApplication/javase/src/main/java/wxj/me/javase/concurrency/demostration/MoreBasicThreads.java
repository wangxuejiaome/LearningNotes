package wxj.me.javase.concurrency.demostration;

/**
 * 1.从输出结果看，不同任务的执行交错进行着。
 * 这种交错是由线程调度器自动控制的，
 * 并且是非确定的，每次运行的结果都可能不同。
 */
public class MoreBasicThreads {

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            new Thread(new LiftOff()).start();
        }
        System.out.println("Waiting for LiftOff");
    }
}
