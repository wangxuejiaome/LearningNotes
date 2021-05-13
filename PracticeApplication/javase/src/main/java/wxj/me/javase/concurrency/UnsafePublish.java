package wxj.me.javase.concurrency;

public class UnsafePublish {

    public Holder mHolder;

    public void initialize() {
        mHolder = new Holder(42);
    }

    public static void main(String[] args) {
        final UnsafePublish unsafePublish = new UnsafePublish();
        final Thread one = new Thread(new Runnable() {
            @Override
            public void run() {
                unsafePublish.initialize();
            }
        });
        one.start();

        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        one.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread() + String.valueOf(unsafePublish.mHolder.getValue()));
                }
            }).start();
        }

    }
}

class Holder {

    private int n;

    public Holder(int n) {
        this.n = n;
        System.out.println(Thread.currentThread() + "initialed");
    }

    public int getValue() {
        return n;
    }
}