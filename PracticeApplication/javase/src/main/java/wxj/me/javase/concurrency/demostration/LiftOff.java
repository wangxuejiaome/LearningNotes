package wxj.me.javase.concurrency.demostration;

/**
 * 需求：定义一个任务
 */
public class LiftOff implements Runnable{

    protected int countDown = 10;
    private static int taskCount = 0;
    private final int id = taskCount++;

    public LiftOff() {

    }

    public LiftOff(int countDown) {
        this.countDown = countDown;
    }

    public String status() {
        return "#"
                + id
                + "("
                + (countDown > 0 ? countDown : "Liftoff!")
                + "),";
    }

    public void run() {
        while (countDown-- > 0) {
            System.out.println(status());
            Thread.yield();
        }
    }
}
