package wxj.me.javase.concurrency.exercise;

class PrintRunnable implements Runnable {

    public static int taskCount = 0;
    public final int id = ++taskCount;

    public PrintRunnable() {
        System.out.println("创建了线程#" + id);
    }

    @Override
    public void run() {
        int count = 0;
        while (++count <= 3) {
            System.out.println("#" + id + "执行了" + count + "次");
            Thread.yield();
        }
        System.out.println("#" + id + "任务结束");
    }
}

public class E1Runnable {
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(new PrintRunnable()).start();
        }
        System.out.println("主线程执行完毕");
    }
}
