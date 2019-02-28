package wxj.me.javase.concurrency.demostration;

class Sleeper extends Thread {
    private int duration;

    public Sleeper(String name, int sleepTime) {
        super(name);
        duration = sleepTime;
        start();
    }

    @Override
    public void run() {
        try {
            sleep(duration);
        } catch (InterruptedException e) {
            // 在其他线程调用该线程的 interrupt() 时，会给该线程设定一个标志，表示这个线程已经被中断了。
            // 但是在异常捕捉的时候，这个标志会被清理，设置为 false，所以在 catch 中捕捉异常的时候这个标志总是返回 false。
            System.out.println(getName() + " was interrupted. "
                    + "isInterrupted(): " + isInterrupted());
            return;
        }
        System.out.println(getName() + " has awakened");
    }
}

class Joiner extends Thread {
    private Sleeper sleeper;

    public Joiner(String name, Sleeper sleeper) {
        super(name);
        this.sleeper = sleeper;
        start();
    }

    @Override
    public void run() {
        try {
            // Joiner 线程调用了 Sleeper 线程的 join() 方法，
            // 那应该要等 Sleeper 线程执行完，再继续执行 Joiner 线程。
            sleeper.join();
        } catch (InterruptedException e) {
            System.out.println("Interrupted");
        }
        System.out.println(getName() + " join completed");
    }
}

public class Joining {

    public static void main(String[] args) {
        Sleeper
                sleepy = new Sleeper("Sleepy", 1500),
                grumpy = new Sleeper("Grumpy", 1500);
        Joiner
                dopey = new Joiner("Dopey", sleepy),
                doc = new Joiner("Doc", grumpy);
        grumpy.interrupt();
    }

   /*
   * Grumpy was interrupted. isInterrupted(): false
     Doc join completed
     Sleepy has awakened
     Dopey join completed
   * */
}
