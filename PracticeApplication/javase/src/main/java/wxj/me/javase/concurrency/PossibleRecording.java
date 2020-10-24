package wxj.me.javase.concurrency;

/**
 * Copyright (C), 2015-2020, suning
 * FileName: PossibleRecording
 * Author: wangxuejiao
 * Date: 2020/3/6 11:34
 * Description:
 * Version: 1.0.0
 */
public class PossibleRecording {
    private static int x = 0, y = 0;
    private static int a = 0, b = 0;

    public static void main(String[] args) throws InterruptedException {
        Thread one = new Thread(new Runnable() {
            @Override
            public void run() {
                a = 1;
                System.out.println("one: a = 1");
                x = b;
                System.out.println("one: x = b");
            }
        });
        Thread two = new Thread(new Runnable() {
            @Override
            public void run() {
                b = 1;
                System.out.println("two: b = 1");
                y = a;
                System.out.println("two: y = a");
            }
        });

        one.start();
        two.start();
        one.join();
        two.join();
        System.out.println("(" + x + "," + y + ")");
    }
}
