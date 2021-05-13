package wxj.me.javase.concurrency.exercise;


class E15EntitySameObjectSynchronized {

    public synchronized void method1() {
        for (int i = 0; i < 100000; i++) {
            System.out.println("method1"  + ", i= " + i);
        }
    }

    public synchronized void method2() {
        for (int i = 0; i < 100000; i++) {
            System.out.println("method2"  + ", i= " + i);
        }
    }

    public synchronized void method3() {
        for (int i = 0; i < 100000; i++) {
            System.out.println("method3" + ", i= " + i);
        }
    }
}

class E15EntityDifferObjectSynchronized {

    private final Object object1 = new Object();
    private final Object object2 = new Object();


    public void method1() {
        synchronized (object1) {
            for (int i = 0; i < 100000; i++) {
                System.out.println("method1"  + ", i= " + i);
            }
        }
    }

    public void method2() {
        synchronized (object2) {
            for (int i = 0; i < 100000; i++) {
                System.out.println("method2"  + ", i= " + i);
            }
        }
    }

    public void method3() {
        synchronized (this) {
            for (int i = 0; i < 100000; i++) {
                System.out.println("method3"  + ", i= " + i);
            }
        }
    }
}


public class E15Synchronized {

    public static void main(String[] args) {

        final E15EntitySameObjectSynchronized entityDifferObjectSynchronized = new E15EntitySameObjectSynchronized();
        //final E15EntityDifferObjectSynchronized entityDifferObjectSynchronized = new E15EntityDifferObjectSynchronized();
        new Thread(new Runnable() {
            @Override
            public void run() {
                entityDifferObjectSynchronized.method1();
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                entityDifferObjectSynchronized.method2();
            }
        }).start();

        entityDifferObjectSynchronized.method3();
    }
}
